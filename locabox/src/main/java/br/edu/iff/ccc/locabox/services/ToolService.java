package br.edu.iff.ccc.locabox.services;

import br.edu.iff.ccc.locabox.entities.Tool;
import br.edu.iff.ccc.locabox.exception.ToolNotExist;
import br.edu.iff.ccc.locabox.repository.ToolRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ToolService {
    @Autowired
    private ToolRepository toolRepository;
    
    public Tool cadastrarFerramenta(Tool tool) {
        return toolRepository.save(tool);
    }

    public Tool findById(Long id) {
        return toolRepository.findById(id).orElseThrow(() -> new ToolNotExist(id));
    }

    public List<Tool> findAll() {
        return toolRepository.findAll();
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