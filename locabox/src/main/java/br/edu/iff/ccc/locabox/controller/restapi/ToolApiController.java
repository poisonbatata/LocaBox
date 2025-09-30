package br.edu.iff.ccc.locabox.controller.restapi;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.edu.iff.ccc.locabox.dto.ToolResponseDTO;
import br.edu.iff.ccc.locabox.entities.Tool;
import br.edu.iff.ccc.locabox.services.ToolService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping(path = "/api/v1/tools")
@Tag(name = "Ferramentas", description = "API para gerenciamento de ferramentas")
public class ToolApiController {

    @Autowired
    private ToolService toolService;

    // GET all tools
    @Operation(summary = "Listar todas as ferramentas", description = "Retorna uma lista de todas as ferramentas cadastradas.")
    @GetMapping
    public ResponseEntity<List<ToolResponseDTO>> getTools() {
        return ResponseEntity.ok(toolService.findAll());
    }

    // GET tool by id
    @Operation(summary = "Buscar ferramenta por ID", description = "Retorna uma ferramenta específica pelo seu ID.")
    @GetMapping("/{id}")
    public ResponseEntity<ToolResponseDTO> getToolById(@PathVariable Long id) {
        return ResponseEntity.ok(toolService.findByIdAsDTO(id));
    }

    // CREATE tool
    @Operation(summary = "Criar nova ferramenta", description = "Adiciona uma nova ferramenta ao sistema.")
    @PostMapping
    public ResponseEntity<Tool> createTool(@RequestBody Tool tool) {
        Tool created = toolService.cadastrarFerramenta(tool);
        return ResponseEntity.ok(created);
    }

    // UPDATE tool
    @Operation(summary = "Atualizar ferramenta", description = "Atualiza os dados de uma ferramenta existente.")
    @PutMapping("/{id}")
    public ResponseEntity<Tool> updateTool(@Parameter(description = "ID da ferramenta") @PathVariable Long id, @RequestBody Tool updatedTool) {
        Tool updated = toolService.updateTool(id, updatedTool);
        return ResponseEntity.ok(updated);
    }

    // DELETE tool
    @Operation(summary = "Deletar ferramenta", description = "Remove uma ferramenta do sistema pelo ID.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTool(@Parameter(description = "ID da ferramenta") @PathVariable Long id) {
        boolean deleted = toolService.deleteToolById(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
