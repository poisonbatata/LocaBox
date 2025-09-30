package br.edu.iff.ccc.locabox.controller.restapi;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.edu.iff.ccc.locabox.entities.UserSystem;
import br.edu.iff.ccc.locabox.exception.UserNotExist;
import br.edu.iff.ccc.locabox.services.UserSystemService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/users")
@Tag(name = "Usuários", description = "API para gerenciamento de usuários do sistema")
public class UserApiController {

    @Autowired
    private UserSystemService userService;

    @Operation(summary = "Listar todos os usuários")
    @GetMapping
    public ResponseEntity<List<UserSystem>> getAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @Operation(summary = "Buscar usuário por ID")
    @GetMapping("/{id}")
    public ResponseEntity<UserSystem> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(userService.findById(id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Buscar usuários por parte do nome (case-insensitive)")
    @GetMapping("/search")
    public ResponseEntity<List<UserSystem>> searchByName(@RequestParam("name") String name) {
        if (name == null || name.isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(userService.searchByNome(name));
    }

    @Operation(summary = "Criar novo usuário")
    @PostMapping
    public ResponseEntity<UserSystem> create(@Valid @RequestBody UserSystem user) {
        UserSystem created = userService.create(user);
        URI location = URI.create("/api/v1/users/" + created.getId());
        return ResponseEntity.created(location).body(created);
    }

    @Operation(summary = "Atualizar usuário")
    @PutMapping("/{id}")
    public ResponseEntity<UserSystem> update(@PathVariable Long id, @Valid @RequestBody UserSystem updated) {
        return ResponseEntity.ok(userService.update(id, updated));
    }

    @Operation(summary = "Deletar usuário")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = userService.deleteById(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
