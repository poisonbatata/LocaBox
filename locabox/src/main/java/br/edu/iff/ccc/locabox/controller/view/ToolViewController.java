package br.edu.iff.ccc.locabox.controller.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.edu.iff.ccc.locabox.entities.Tool;
import br.edu.iff.ccc.locabox.services.ToolService;

@Controller
@RequestMapping(path = "/tool")
public class ToolViewController {

    // CONTROLLERS DO TOOL
    /* Preciso:
        - Criar uma ferramenta
        - Editar uma ferramenta
        - Deletar uma ferramenta
        - Listar ferramentas (filtros por categoria, preço, localização, disponibilidade)
        - Ver detalhes de uma ferramenta
        - Buscar ferramentas
        - Ver ferramentas de um usuário
     */

    @Autowired
    private ToolService toolService;

    @GetMapping(path = "/{id}")
    public String getToolById(@PathVariable("id") Long id, Model model) {
        // 1) Busca a ferramenta
        Tool tool = toolService.findById(id);
        if (tool == null) {
            return "redirect:/tool/list";
        }

        // 2) Mapeia os campos do domínio -> variáveis do template
        // Título e descrição
        model.addAttribute("productTitle", tool.getNome());
        model.addAttribute("productDescription", tool.getDescricao());

        // Preço (por dia)
        model.addAttribute("pricePerDay", tool.getPreco());

        // Mínimo de dias / caução (ajuste conforme seu domínio; aqui vão defaults)
        model.addAttribute("minDays", 1);
        model.addAttribute("depositValue", 0);

        // Imagens: pega da String "fotos" (separadas por vírgula, ponto-e-vírgula ou espaço)
        List<String> imgs = parseFotos(tool.getFotos());
        String main = imgs.isEmpty() ? "/images/placeholder.png" : imgs.get(0);
        List<String> thumbs = imgs.size() > 1 ? imgs.subList(1, imgs.size()) : java.util.Collections.emptyList();
        model.addAttribute("mainImageUrl", main);
        model.addAttribute("imageUrls", thumbs);

        // Cabeçalho (avatar do usuário logado, se tiver; por enquanto um placeholder)
        model.addAttribute("userImageUrl", "/images/avatar.png");

        // Disponibilidade / calendário (gera um grid do mês corrente)
        java.time.YearMonth ym = java.time.YearMonth.now();
        java.util.Locale br = new java.util.Locale("pt","BR");
        model.addAttribute("currentMonth", ym.format(java.time.format.DateTimeFormatter.ofPattern("MMMM yyyy", br)));
        model.addAttribute("weekdays", java.util.List.of("D","S","T","Q","Q","S","S"));
        model.addAttribute("calendarDays", buildCalendar(ym, tool.getDisponibilidade()));

        // Vendedor (ajuste se tiver relacionamento proprietário->ferramenta)
        model.addAttribute("sellerImageUrl", "/images/seller.png");
        model.addAttribute("sellerName", "Proprietário");
        model.addAttribute("sellerRating", 4.8);
        model.addAttribute("sellerReviews", 0);

        // Avaliações (se ainda não implementou, mantemos vazio/zero)
        model.addAttribute("avgReview", 0);
        model.addAttribute("totalReviews", 0);
        model.addAttribute("reviewStats", java.util.Collections.emptyList());
        model.addAttribute("reviews", java.util.Collections.emptyList());

        // 3) Devolve o novo template segmentado
        return "tool/detail";
    }

    // Exibe formulário de cadastro
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("toolDTO", new br.edu.iff.ccc.locabox.dto.ToolDTO());
        return "tool/createTool";
    }

    // Salva nova ferramenta
    @PostMapping("/create")
    public String createTool(@ModelAttribute("toolDTO") br.edu.iff.ccc.locabox.dto.ToolDTO toolDTO) {
        // Aqui você pode converter o DTO para entidade Tool antes de salvar
        Tool tool = new Tool();
        tool.setNome(toolDTO.getNome());
        tool.setDescricao(toolDTO.getDescricao());
        tool.setCategoria(toolDTO.getCategoria());
        tool.setPreco(toolDTO.getPreco());
        tool.setCondicao(toolDTO.getCondicao());
        tool.setDisponibilidade(toolDTO.getDisponibilidade());
        tool.setFotos(toolDTO.getFotos());
        toolService.cadastrarFerramenta(tool);
        return "redirect:/tool/list";
    }
    
    @PostMapping(path = "/edit")
    @ResponseBody
    public String editTool(
            @RequestBody Map<String, Object> toolData) {
        System.out.println("Produto editado:");
        System.out.println("Dados do produto: " + toolData);

        return "Produto editado com sucesso: " + toolData.get("nome");
    }

    @PostMapping(path = "/delete")
    @ResponseBody
    public String deleteTool(
            @RequestParam String toolId) {
        System.out.println("Produto deletado:");
        System.out.println("ID do produto: " + toolId);

        return "Produto deletado com sucesso: " + toolId;
    }

    //@GetMapping(path = "/list")
    //   /* @ResponseBody
    //public List<Map<String, Object>> listTools() {
    //    List<Map<String, Object>> tools = new ArrayList<>();
    //    
    //    // Mock de ferramentas
    //    for (int i = 1; i <= 5; i++) {
    //        Map<String, Object> tool = new HashMap<>();
    //        tool.put("id", i);
    //        tool.put("name", "Furadeira " + i);
    //        tool.put("description", "Descrição da furadeira " + i);
    //        tool.put("price", 25.0 * i);
    //        tool.put("imageUrl", "https://example.com/tool" + i + ".jpg");
    //        tools.add(tool);
    //    }
    //    
    //    return tools;
    //}*/
    //public String listTools(Model model) {
    //    model.addAttribute("tools", toolService.listarFerramentas());
    //    return "tool/toolDetailHome";
    //}

    //@GetMapping(path = "/search")
    //@ResponseBody
    //public List<Map<String, Object>> searchTools(@RequestParam String query) {
    //    List<Map<String, Object>> tools = new ArrayList<>();
    //    
    //    // Mock de ferramentas filtradas pela query
    //    for (int i = 1; i <= 5; i++) {
    //        if (("Furadeira " + i).toLowerCase().contains(query.toLowerCase())) {
    //            Map<String, Object> tool = new HashMap<>();
    //            tool.put("id", i);
    //            tool.put("name", "Furadeira " + i);
    //            tool.put("description", "Descrição da furadeira " + i);
    //            tool.put("price", 25.0 * i);
    //            tool.put("imageUrl", "/images/furadeira" + i + ".jpg");
    //            tools.add(tool);
    //        }
    //    }
    //    
    //    return tools;
    //}

    @GetMapping("/search")
    public String search(
            @RequestParam(value = "q", required = false) String q,
            @RequestParam(value = "page", defaultValue = "1") int page,
            Model model) {

        int pageSize = 12;

        // 1) Buscar/filtrar (veja item 5 para o service)
        List<Tool> all = toolService.findAll(); // novo método simples
        List<Tool> filtered = (q == null || q.isBlank())
                ? all
                : all.stream()
                    .filter(t -> t.getNome() != null && t.getNome().toLowerCase().contains(q.toLowerCase()))
                    .toList();

        // 2) Paginar (simplificado)
        int total = filtered.size();
        int totalPages = Math.max(1, (int) Math.ceil(total / (double) pageSize));
        int from = Math.max(0, (page - 1) * pageSize);
        int to = Math.min(total, from + pageSize);
        List<Tool> pageItems = from < to ? filtered.subList(from, to) : List.of();

        // 3) Mapear para o DTO de card
        List<br.edu.iff.ccc.locabox.dto.ToolCardDTO> cards = pageItems.stream()
                .map(br.edu.iff.ccc.locabox.mapper.ToolCardMapper::toCard)
                .toList();

        // 4) Model
        model.addAttribute("pageTitle", "LocaBox — Buscar");
        model.addAttribute("q", q);
        model.addAttribute("filters", List.of("Category", "Price", "Location", "Rating"));
        model.addAttribute("tools", cards);
        model.addAttribute("page", page);
        model.addAttribute("totalPages", totalPages);

        return "search";
    }


    @GetMapping(path = "/user/{userId}")
    public String getUserTools(@PathVariable("userId") String userId, Model model) {
        model.addAttribute("userId", userId);
        model.addAttribute("userName", "Usuário " + userId);
        model.addAttribute("tools", toolService.findAll()); // Mock de ferramentas do usuário
        return "tool/toolDetailHome";
    }










    // HELPERS
    private static java.util.List<String> parseFotos(String fotos) {
    if (fotos == null || fotos.isBlank()) return new java.util.ArrayList<>();
    return java.util.Arrays.stream(fotos.split("[,;\\s]+"))
            .filter(s -> s != null && !s.isBlank())
            .toList();
}

private static java.util.List<java.util.Map<String, Object>> buildCalendar(java.time.YearMonth ym, String disponibilidade) {
    java.util.List<java.util.Map<String, Object>> days = new java.util.ArrayList<>();

    java.time.LocalDate first = ym.atDay(1);
    // Domingo = 0, Segunda = 1, ... (ajuste para grid iniciar no domingo)
    int shift = first.getDayOfWeek().getValue() % 7;
    java.time.LocalDate start = first.minusDays(shift);

    for (int i = 0; i < 42; i++) {
        java.time.LocalDate d = start.plusDays(i);
        java.util.Map<String, Object> entry = new java.util.HashMap<>();
        entry.put("day", d.getMonth() == ym.getMonth() ? d.getDayOfMonth() : "");

        // Regra simplificada: se disponibilidade for "Indisponível", marca todo o mês como indisponível
        boolean unavailable = d.getMonth() == ym.getMonth()
                && disponibilidade != null
                && disponibilidade.equalsIgnoreCase("Indisponível");

        entry.put("unavailable", unavailable);
        entry.put("selected", false);
        days.add(entry);
    }
    return days;
}

    
}
