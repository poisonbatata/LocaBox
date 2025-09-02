package br.edu.iff.ccc.locabox.controller.view;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.iff.ccc.locabox.entities.UserSystem;
import br.edu.iff.ccc.locabox.services.UserSystemService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.edu.iff.ccc.locabox.dto.UserProfileDTO;
import br.edu.iff.ccc.locabox.mapper.UserProfileMapper;


@Controller
@RequestMapping(path = "/user")
public class UserViewController {
    
    // CONTROLLERS DO USER
    /* Preciso:
        - Criar um usuário
        - Logar um usuário
        - Deslogar um usuário
        - Editar um usuário
        - Deletar um usuário
        - Ver detalhes de um usuário
        - Esqueci minha senha
    */

    @Autowired
    private UserSystemService userSystemService;

    @GetMapping(path = "/{id}")
    public String getUserById(@PathVariable("id") Long id, Model model) {
        var user = userSystemService.getUserSystemById(id); // já existe no seu service :contentReference[oaicite:4]{index=4}
        if (user == null) {
            model.addAttribute("error", "User not found");
            return "errorView.html";
        }

        UserProfileDTO profile = UserProfileMapper.toDTO(user);
        model.addAttribute("profile", profile);

        // distribuição fake (5→1). Se tiver DTO próprio, pode trocar.
        record Stat(int score, int percent) {}
        model.addAttribute("distribution", java.util.List.of(
            new Stat(5, 40), new Stat(4, 30), new Stat(3, 15), new Stat(2, 10), new Stat(1, 5)
        ));

        // listings do usuário — se ainda não tem vínculo, envia vazio (template trata)
        model.addAttribute("listings", java.util.List.of());

        model.addAttribute("pageTitle", "LocaBox — Profile");
        return "user/profile";
    }

    @GetMapping("/signup")
    public String signupPage(@RequestParam(value="name", required=false) String name,
                             @RequestParam(value="email", required=false) String email,
                             @RequestParam(value="error", required=false) String error,
                             Model model) {
        model.addAttribute("pageTitle", "LocaBox — Criar conta");
        if (name != null) model.addAttribute("name", name);
        if (email != null) model.addAttribute("email", email);
        if (error != null) model.addAttribute("error", error);
        return "signup";
    }


    @PostMapping("/signup")
    public String doSignup(@RequestParam String name,
                           @RequestParam String email,
                           @RequestParam String password,
                           @RequestParam String confirmPassword,
                           HttpSession session,
                           RedirectAttributes ra) {

        // validações simples
        if (name == null || name.isBlank() || email == null || email.isBlank()) {
            ra.addFlashAttribute("error", "Informe nome e e-mail.");
            ra.addAttribute("name", name);
            ra.addAttribute("email", email);
            return "redirect:/user/signup";
        }
        if (!password.equals(confirmPassword)) {
            ra.addFlashAttribute("error", "As senhas não conferem.");
            ra.addAttribute("name", name);
            ra.addAttribute("email", email);
            return "redirect:/user/signup";
        }
        if (userSystemService.existsByEmail(email)) {
            ra.addFlashAttribute("error", "Já existe uma conta com esse e-mail.");
            ra.addAttribute("name", name);
            ra.addAttribute("email", email);
            return "redirect:/user/signup";
        }

        // cria usuário (service cuida de ID e possíveis campos extras)
        //UserSystem user = userSystemService.createUserSystem(name, email, password);

        // auto-login
        //session.setAttribute("currentUser", user);

        ra.addFlashAttribute("success", "Conta criada com sucesso!");
        return "redirect:/user/1";
    }

    //@PostMapping(path = "/signup")
    //public String createUser(@Valid UserSystem user, BindingResult errors, Model model) {
    //    if (errors.hasErrors()) {
    //        model.addAttribute("errors", errors.getAllErrors());
    //        return "userFormView.html";
    //    }else if(userSystemService.userSystemExists(user.getId())) {
    //        model.addAttribute("error", "User with this ID already exists");
    //        return "errorView.html";
    //    }
    //
    //    UserSystem createdUser = userSystemService.createUserSystem(user.getId(), user.getNome(), user.getEmail(), user.getStatus(), user.getRole());
    //
    //   model.addAttribute("user", createdUser);
    //   return "userDetailHome.html";
    //}
    


    @GetMapping(path="/login")
    public String loginPage(Model model, @RequestParam(value = "error", required = false) String error, @RequestParam(value = "logout", required = false) String logout) {
        model.addAttribute("pageTitle", "LocaBox — Entrar");
        return "login";
    }
    

    @PostMapping("/login")
    public String doLogin(@RequestParam Map<String,String> params,
                        HttpSession session,
                        RedirectAttributes ra) {
        String email = params.get("email");
        if (email == null || email.isBlank()) {
            email = params.get("username");
        }

        if (email == null || email.isBlank()) {
            return "redirect:/user/login?error";
        }

        UserSystem user = userSystemService.findByEmail(email);
        if (user == null) {
            return "redirect:/user/login?error";
        }

        // Não verifica senha por enquanto
        session.setAttribute("currentUser", user);
        return "redirect:/user/1";
    }

    @GetMapping("/logout")
    public String doLogout(HttpSession session) {
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/user/login?logout";
    }

    @PostMapping(path = "/edit")
    @ResponseBody
    public String editUser(
            @RequestBody Map<String, Object> userData) {
        System.out.println("Usuário editado:");
        System.out.println("Dados do usuário: " + userData);

        return "Usuário editado com sucesso: " + userData.get("nome");
    }

    @PostMapping(path = "/delete")
    @ResponseBody
    public String deleteUser(
            @RequestParam String userId) {
        System.out.println("Usuário deletado:");
        System.out.println("ID do usuário: " + userId);

        return "Usuário deletado com sucesso: " + userId;
    }

    

    @GetMapping(path = "/forgot-password")
    @ResponseBody
    public String forgotPassword(
            @RequestParam String email) {
        System.out.println("Solicitação de recuperação de senha:");
        System.out.println("Email: " + email);

        return "Instruções de recuperação de senha enviadas para: " + email;
    }







}




    