package br.edu.iff.ccc.locabox.services;

import br.edu.iff.ccc.locabox.dto.ToolResponseDTO;
import br.edu.iff.ccc.locabox.dto.UserSystemResponseDTO;
import br.edu.iff.ccc.locabox.entities.Tool;
import br.edu.iff.ccc.locabox.exception.ToolNotExist;
import br.edu.iff.ccc.locabox.repository.ToolRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class ToolService {
    @Autowired
    private ToolRepository toolRepository;


    // Método para converter uma entidade em DTO
    private ToolResponseDTO toDTO(Tool tool) {
        UserSystemResponseDTO ownerDTO = null;
        if (tool.getOwner() != null) { // Evita NullPointerException se a ferramenta não tiver dono
            ownerDTO = new UserSystemResponseDTO(
                tool.getOwner().getId(),
                tool.getOwner().getNome(),
                tool.getOwner().getEmail(),
                tool.getOwner().getStatus(),
                tool.getOwner().getRole()
            );
        }
        return new ToolResponseDTO(tool.getId(), tool.getNome(), tool.getDescricao(), tool.getCategoria(), tool.getPreco(), tool.getCondicao(), tool.getDisponibilidade(), ownerDTO);
    }

    // Altere os métodos públicos para retornarem DTOs
    public List<ToolResponseDTO> findAll() {
        return toolRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public ToolResponseDTO findByIdAsDTO(Long id) {
        Tool tool = toolRepository.findById(id).orElseThrow(() -> new ToolNotExist(id));
        return toDTO(tool);
    }

    public Tool findById(Long id) {
        return toolRepository.findById(id).orElseThrow(() -> new ToolNotExist(id));
    }
    
    public Tool cadastrarFerramenta(Tool tool) {
        return toolRepository.save(tool);
    }

    public Tool findByName(String name) {
        return toolRepository.findByName(name);
    }

    public boolean deleteToolById(Long id) {
        if (toolRepository.existsById(id)) {
            toolRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Tool updateTool(Long id, Tool updatedTool) {
        Tool existing = toolRepository.findById(id).orElseThrow(() -> new ToolNotExist(id));
        existing.setNome(updatedTool.getNome());
        existing.setDescricao(updatedTool.getDescricao());
        existing.setCategoria(updatedTool.getCategoria());
        existing.setPreco(updatedTool.getPreco());
        existing.setCondicao(updatedTool.getCondicao());
        existing.setDisponibilidade(updatedTool.getDisponibilidade());
        existing.setFotos(updatedTool.getFotos());
        return toolRepository.save(existing);
    }
}