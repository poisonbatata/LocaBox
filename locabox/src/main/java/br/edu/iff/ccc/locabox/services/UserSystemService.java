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







    
}
