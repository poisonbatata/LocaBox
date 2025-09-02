// HomeController.java
package br.edu.iff.ccc.locabox.controller.view;

import br.edu.iff.ccc.locabox.services.ToolService;
import br.edu.iff.ccc.locabox.dto.ToolCardDTO;
import br.edu.iff.ccc.locabox.mapper.ToolCardMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    private final ToolService toolService;

    public HomeController(ToolService toolService) {
        this.toolService = toolService;
    }

    @GetMapping("/")
    public String index(Model model) {
        // Populares simples: primeiros N itens (troque por métrica real quando tiver)
        var popular = toolService.findAll().stream()
                .limit(8)
                .map(ToolCardMapper::toCard)
                .toList();

        model.addAttribute("popularItems", popular);
        model.addAttribute("pageTitle", "LocaBox — Encontre e alugue perto de você");
        return "index";
    }
}
