package br.edu.iff.ccc.locabox.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.iff.ccc.locabox.entities.UserSystem;

public interface UserSystemRepository extends JpaRepository<UserSystem, Long> {
    UserSystem findByEmail(String email);
    List<UserSystem> findByNomeContainingIgnoreCase(String nome);
    boolean existsByEmail(String email);
}