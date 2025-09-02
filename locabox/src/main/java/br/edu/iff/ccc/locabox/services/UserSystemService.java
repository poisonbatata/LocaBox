package br.edu.iff.ccc.locabox.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.stereotype.Service;

import br.edu.iff.ccc.locabox.entities.UserSystem;

@Service
public class UserSystemService {
    
    public UserSystem createUserSystem(Long id, String nome, String email, String status, String role) {
        return new UserSystem(id, nome, email, status, role);
    }

    public UserSystem updateUserSystem(UserSystem user, String nome, String email, String status, String role) {
        if (nome != null && !nome.isEmpty()) {
            user.setNome(nome);
        }
        if (email != null && !email.isEmpty()) {
            user.setEmail(email);
        }
        if (status != null && !status.isEmpty()) {
            user.setStatus(status);
        }
        if (role != null && !role.isEmpty()) {
            user.setRole(role);
        }
        return user;
    }

    public void deleteUserSystem(UserSystem user) {
        // Implement deletion logic here
    }

    public UserSystem getUserSystemById(long id) {
        if(id == 1L){
            return new UserSystem(1L, "John Doe", "john.doe@example.com", "active", "user");
        } else if(id == 2L){
            return new UserSystem(2L, "Jane Smith", "jane.smith@example.com", "inactive", "admin");
        }
        return null;
    }

    public boolean userSystemExists(Long id) {
        return getUserSystemById(id) != null;
    }

    public List<UserSystem> getAllUserSystems() {
        List<UserSystem> users = new ArrayList<>();
        users.add(new UserSystem(1L, "John Doe", "john.doe@example.com", "active", "user"));
        users.add(new UserSystem(2L, "Jane Smith", "jane.smith@example.com", "inactive", "admin"));
        return users;
    }

    public List<UserSystem> getUserSystemsByStatus(String status) {
        List<UserSystem> users = getAllUserSystems();
        List<UserSystem> filteredUsers = new ArrayList<>();
        for (UserSystem user : users) {
            if (user.getStatus().equalsIgnoreCase(status)) {
                filteredUsers.add(user);
            }
        }
        return filteredUsers;
    }

    public UserSystem findByEmail(String email) {
        if (email == null) return null;
        List<UserSystem> users = getAllUserSystems();
        for (UserSystem user : users) {
            if (user.getEmail() != null && user.getEmail().equalsIgnoreCase(email)) {
                return user;
            }
        }
        return null;
    }

    public boolean existsByEmail(String email) {
        if (email == null) return false;
        String s = email.toLowerCase();
        List<UserSystem> users = getAllUserSystems();
        return users.stream().anyMatch(u -> u.getEmail()!=null && u.getEmail().equalsIgnoreCase(s));
    }

    //public UserSystem register(String name, String email, String rawPassword) {
    //    List<UserSystem> users = getAllUserSystems();
    //    UserSystem u = new UserSystem();
    //    // ID
    //    try {
    //        u.setId(seq.getAndIncrement());
    //    } catch (NoSuchMethodError | Exception ignore) {
    //        // se a sua classe usa outro tipo/forma de ID, ajuste aqui
    //    }
    //    // Nome (aceita get/setName OU get/setNome)
    //    try { u.setName(name); } catch (NoSuchMethodError | Exception e) {
    //        try { u.setNome(name); } catch (Exception ignore) {}
    //    }
    //    // Email
    //    u.setEmail(email);
    //
    //    // Se houver campo de senha na sua entidade, salve-a (sem encoder, por enquanto)
    //    try { u.setPassword(rawPassword); } catch (NoSuchMethodError | Exception ignore) {}
    //
    //    // Se houver campos extras (status/role), defina defaults
    //    try { u.setStatus("ATIVO"); } catch (NoSuchMethodError | Exception ignore) {}
    //    try { u.setRole("USER"); }   catch (NoSuchMethodError | Exception ignore) {}
    //
    //    users.add(u);
    //    return u;
    //
    //}
   
}
