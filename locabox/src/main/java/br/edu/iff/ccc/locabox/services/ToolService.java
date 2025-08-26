package br.edu.iff.ccc.locabox.services;

import br.edu.iff.ccc.locabox.entities.Tool;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ToolService {
    private final List<Tool> tools = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public ToolService() {
        // Dados mockados
        tools.add(new Tool(idGenerator.getAndIncrement(), "Furadeira Bosch", "Furadeira potente 500W", "Elétrica", 30.0, "Nova", "Disponível", "/images/furadeira1.png"));
        tools.add(new Tool(idGenerator.getAndIncrement(), "Serra Circular", "Serra para madeira", "Elétrica", 45.0, "Usada", "Disponível", "/images/serra-circular.jpg"));
        tools.add(new Tool(idGenerator.getAndIncrement(), "Martelo", "Martelo de aço", "Manual", 10.0, "Nova", "Indisponível", "/images/martelo.jpg"));
    }

    public List<Tool> listarFerramentas() {
        return tools;
    }

    public Tool cadastrarFerramenta(Tool tool) {
        tool.setId(idGenerator.getAndIncrement());
        tools.add(tool);
        return tool;
    }

    public Tool findById(String id) {
        return tools.stream()
                .filter(tool -> tool.getId() == Long.parseLong(id))
                .findFirst()
                .orElse(null);
    }
}