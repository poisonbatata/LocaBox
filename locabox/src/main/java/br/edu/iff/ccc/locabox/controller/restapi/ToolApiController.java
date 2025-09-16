package br.edu.iff.ccc.locabox.controller.restapi;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    })
    @GetMapping
    public ResponseEntity<List<Tool>> getTools() {
        return ResponseEntity.ok(toolService.findAll());
    }

    // GET tool by id
    @Operation(summary = "Buscar ferramenta por ID", description = "Retorna uma ferramenta específica pelo seu ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ferramenta encontrada"),
        @ApiResponse(responseCode = "404", description = "Ferramenta não encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Tool> getToolById(@Parameter(description = "ID da ferramenta") @PathVariable Long id) {
        Tool tool = toolService.findById(id);
        if (tool == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tool);
    }

    // CREATE tool
    @Operation(summary = "Criar nova ferramenta", description = "Adiciona uma nova ferramenta ao sistema.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ferramenta criada com sucesso")
    })
    @PostMapping
    public ResponseEntity<Tool> createTool(@RequestBody Tool tool) {
        Tool created = toolService.cadastrarFerramenta(tool);
        return ResponseEntity.ok(created);
    }

    // UPDATE tool
    @Operation(summary = "Atualizar ferramenta", description = "Atualiza os dados de uma ferramenta existente.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ferramenta atualizada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Ferramenta não encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Tool> updateTool(@Parameter(description = "ID da ferramenta") @PathVariable Long id, @RequestBody Tool updatedTool) {
    Tool updated = toolService.updateTool(id, updatedTool);
    return ResponseEntity.ok(updated);
    }

    // DELETE tool
    @Operation(summary = "Deletar ferramenta", description = "Remove uma ferramenta do sistema pelo ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Ferramenta deletada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Ferramenta não encontrada")
    })
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
