package br.edu.iff.ccc.locabox.entities;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

@Entity
public class UserSystem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @NotEmpty(message = "Nome não pode estar vazio")
    private String nome;

    @Email(message = "Email inválido")
    @NotEmpty(message = "Email não pode estar vazio")
    private String email;

    private String status;
    private String role;

    public UserSystem(Long id, String nome, String email, String status, String role) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.status = status;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getStatus() {
        return status;
    }

    public String getRole() {
        return role;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome2) {
        this.nome = nome2;
    }

    public void setEmail(String email2) {
        this.email = email2;
    }
    

    public void setStatus(String status2) {
        this.status = status2;
    }
    

    public void setRole(String role2) {
        this.role = role2;
    }

    
}