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
    
    public void cadastrarFerramenta(Tool tool) {
        toolRepository.save(tool);
    }

    public Tool findById(String id) {
        return toolRepository.findById(Long.parseLong(id)).orElseThrow(() -> new ToolNotExist(Long.parseLong(id)));
    }

    public List<Tool> findAll() {
        return toolRepository.findAll();
    }

    public Tool findByName(String name) {
        return toolRepository.findByName(name);
    }
}