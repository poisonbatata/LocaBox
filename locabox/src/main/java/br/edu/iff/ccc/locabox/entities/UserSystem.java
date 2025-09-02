package br.edu.iff.ccc.locabox.entities;

import java.io.Serializable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "user_system",
       uniqueConstraints = @UniqueConstraint(name = "uk_user_system_email", columnNames = "email"))
public class UserSystem implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Nome não pode estar vazio")
    @Column(nullable = false, length = 120)
    private String nome;

    @Email(message = "Email inválido")
    @NotEmpty(message = "Email não pode estar vazio")
    @Column(nullable = false, length = 180)
    private String email;

    @Column(nullable = false, length = 30)
    private String status;

    @Column(nullable = false, length = 30)
    private String role;

    protected UserSystem() {} // JPA

    public UserSystem(Long id, String nome, String email, String status, String role) {
        this.id = id; this.nome = nome; this.email = email; this.status = status; this.role = role;
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