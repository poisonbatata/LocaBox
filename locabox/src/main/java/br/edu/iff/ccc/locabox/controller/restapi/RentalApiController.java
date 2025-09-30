package br.edu.iff.ccc.locabox.controller.restapi;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.edu.iff.ccc.locabox.dto.RentalRequestDTO;
import br.edu.iff.ccc.locabox.dto.RentalResponseDTO;
import br.edu.iff.ccc.locabox.entities.Rental;
import br.edu.iff.ccc.locabox.services.RentalService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/rentals")
@Tag(name = "Rentals", description = "API para gerenciamento de locações")
public class RentalApiController {

    @Autowired
    private RentalService rentalService;

    @Operation(summary = "Listar todas as locações")
    //@ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<RentalResponseDTO>> getAll() {
        return ResponseEntity.ok(rentalService.findAll());
    }

    @Operation(summary = "Obter locação por ID")
    //@ApiResponse(responseCode = "200", description = "Locação encontrada")
    @GetMapping("/{id}")
    public ResponseEntity<RentalResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(rentalService.findByIdAsDTO(id));
    }

    @Operation(summary = "Buscar locações por status")
    @GetMapping("/by-status")
    public ResponseEntity<List<RentalResponseDTO>> findByStatus(@RequestParam("value") Rental.Status status) {
        return ResponseEntity.ok(rentalService.findByStatus(status));
    }

    @Operation(summary = "Buscar locações por usuário (renter)")
    @GetMapping("/by-renter/{renterId}")
    public ResponseEntity<List<RentalResponseDTO>> findByRenter(@PathVariable Long renterId) {
        return ResponseEntity.ok(rentalService.findByRenter(renterId));
    }

    @Operation(summary = "Buscar locações por ferramenta")
    @GetMapping("/by-tool/{toolId}")
    public ResponseEntity<List<RentalResponseDTO>> findByTool(@PathVariable Long toolId) {
        return ResponseEntity.ok(rentalService.findByTool(toolId));
    }

    @Operation(summary = "Criar nova locação")
    //@ApiResponse(responseCode = "201", description = "Locação criada")
    @PostMapping
    public ResponseEntity<RentalResponseDTO> create(@Valid @RequestBody RentalRequestDTO dto) {
        RentalResponseDTO created = rentalService.create(dto);
        URI location = URI.create("/api/v1/rentals/" + created.getId());
        return ResponseEntity.created(location).body(created);
    }

    @Operation(summary = "Atualizar locação")
    //@ApiResponse(responseCode = "200", description = "Locação atualizada")
    @PutMapping("/{id}")
    public ResponseEntity<RentalResponseDTO> update(@PathVariable Long id, @Valid @RequestBody RentalRequestDTO dto) {
        return ResponseEntity.ok(rentalService.update(id, dto));
    }

    @Operation(summary = "Atualizar status da locação")
    //@ApiResponse(responseCode = "200", description = "Status atualizado")
    @PatchMapping("/{id}/status")
    public ResponseEntity<RentalResponseDTO> updateStatus(@PathVariable Long id, @RequestParam("status") Rental.Status status) {
        return ResponseEntity.ok(rentalService.updateStatus(id, status));
    }

    @Operation(summary = "Excluir locação")
    //@ApiResponse(responseCode = "204", description = "Locação deletada")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return rentalService.deleteById(id)
            ? ResponseEntity.noContent().build()
            : ResponseEntity.notFound().build();
    }
}