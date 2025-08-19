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
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



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
        UserSystem user = userSystemService.getUserSystemById(id);
        if(user != null) {
            model.addAttribute("user", user);
            return "userDetailHome.html";
        } else {
            model.addAttribute("error", "User not found");
            return "errorView.html";
        }
    }

    @PostMapping(path = "/signup")
    public String createUser(@Valid UserSystem user, BindingResult errors, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("errors", errors.getAllErrors());
            return "userFormView.html";
        }else if(userSystemService.userSystemExists(user.getId())) {
            model.addAttribute("error", "User with this ID already exists");
            return "errorView.html";
        }

        UserSystem createdUser = userSystemService.createUserSystem(user.getId(), user.getNome(), user.getEmail(), user.getStatus(), user.getRole());

       model.addAttribute("user", createdUser);
       return "userDetailHome.html";
    }
    
        @PostMapping(path = "/login")
    @ResponseBody
    public String loginUser(
            @RequestParam String email,
            @RequestParam String senha) {
        System.out.println("Usuário logado:");
        System.out.println("Email: " + email);
        System.out.println("Senha: " + senha);

        return "Usuário logado com sucesso: " + email;
    }

    @PostMapping(path = "/logout")
    @ResponseBody
    public String logoutUser(
            @RequestParam String userId) {
        System.out.println("Usuário deslogado:");
        System.out.println("ID do usuário: " + userId);

        return "Usuário deslogado com sucesso: " + userId;
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





    