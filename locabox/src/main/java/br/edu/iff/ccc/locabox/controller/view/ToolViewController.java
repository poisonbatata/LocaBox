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
    public String getToolById(@PathVariable("id") String id, Model model) {
        // Título e descrição do produto
        /*
        model.addAttribute("productTitle", "Furadeira sem fio 50V Max");
        model.addAttribute("productDescription", "Esta furadeira sem fio 20V Max é perfeita para diversas tarefas domésticas. Motor potente, transmissão de duas velocidades e embreagem ajustável para controle preciso. Acompanha 2 baterias e carregador.");

        // Imagens do produto
        model.addAttribute("mainImageUrl", "https://lh3.googleusercontent.com/aida-public/AB6AXuDMA8YcuBraFe85ucfpqTMMqoLCoAp592t_SeQHbyzy7ijjT1gz1jNwsGGFKRvrQnJJLBb6K8JszrNqqZ5FZVm_yFcB3Mkf2zJ7dKBvzfHjRqEn8o68J2VDvZyHjjNZnQk6yc9A4YKbhGvlUtUWZG-BcVrloGMqmKsMWpEHI9jA001BikQlMAN85zanJSByvXXMHWwqr8hUlVoXL9-HQBdDEPf9MEF8R54bqgnMiIAiSkSf22UL13LR_lJSnYCjRY4woh-Wm6zXEms");
        model.addAttribute("imageUrls", List.of(
            "https://lh3.googleusercontent.com/aida-public/AB6AXuAEBLPcVG4Hla5rvGe_jPsa9c-mvedA0liY23W_Rf4389XECiaPc3DbOBAOAqrTXtte8oHgxSiGW7OWG2ML3jy4y2giYxUiYo7hatIxd2A71nwznBRy1sPrS-diMlY3bVDNyyIgNYzyve2V0dZwAyjNBKtutVu1Ni1AEEUWAfqAfGUmqR0_oxN-GK3cU5S_ksKKZueFFc639kYEu_olrbWnFbeitvkcrTVlNXzFOGDIxzkeFtAtmFStfblFitEpot0g7g1RTyoMBHE",
            "https://lh3.googleusercontent.com/aida-public/AB6AXuDy--0jD4M7E7VNJ1kZlLVgsQgyYRb309Hrbc5qNJPE9JJFBG3R_WkNIAMZNLowdc04LBVUQIQhA1S2_Fw-8x0lKUlQCAf-6PisEus8--LvJuAACoM5PmTjzli0bEN96kTrEnBbHTgky1xGTXSd6zvWapPh2Cmv-YDY-t61Hu1fFEZYHIErEAPI-DD8LphqIDPPNYhHZrsFx_pFnp9uvLqcX-TqWgePafp6BZDn8df43LIj-TgTg5GsJqSL41TaXP2rmr4M7Ah8Tnc"
            ));
            
            // Preço e aluguel
            model.addAttribute("pricePerDay", 25);
            model.addAttribute("minDays", 1);
            model.addAttribute("depositValue", 50);
            
            // Disponibilidade (exemplo de dias e calendário)
            model.addAttribute("currentMonth", "Julho 2024");
            model.addAttribute("weekdays", List.of("D", "S", "T", "Q", "Q", "S", "S"));
            // Mock de dias do mês (supondo o dia 5 e 30 marcados/unavailable)
            List<Map<String, Object>> calendarDays = new ArrayList<>();
            for (int i = 1; i <= 30; i++) {
                Map<String, Object> day = new HashMap<>();
                day.put("day", i);
                day.put("selected", i == 5 || i == 30);  // Exemplo: selecionado nos dias 5 e 30
                day.put("unavailable", i % 7 == 0);      // Exemplo: indisponível aos domingos
                calendarDays.add(day);
            }
            model.addAttribute("calendarDays", calendarDays);
            
            // Dados do vendedor
            model.addAttribute("sellerName", "Emily Carter");
            model.addAttribute("sellerImageUrl", "https://lh3.googleusercontent.com/aida-public/AB6AXuAjAvCtgTRt0daCd1w8uYj7-Ch0wv_Ui-QVat7WNj5-xpFFeaNsW5mnmk_-idl-AR3fPfA_oRobLw0iHP4mfC7rupsaNn4fY7wjkUAHf9-RNSrsWj6rH3YbBdCE7CP0SpCa19qg_LbejQrImNNzIdx4WtxIfUiBLSuHCtfBF2RaWmSPfbjEyPxuZI37x8XWRKm6vRy0zTSDb_X5hh6SeGfnHRadSZZuZ-nxXWWUydAjcKQ-KGf0YW5pa0QD7DlNICY0EkgO3WhDBqQ");
            model.addAttribute("sellerRating", 4.8);
            model.addAttribute("sellerReviews", 125);
            
            // Dados do usuário logado (avatar)
            model.addAttribute("userImageUrl", "https://lh3.googleusercontent.com/aida-public/AB6AXuD5pPEXWPP4OJSapP0PrdYye7RSoedN23107N7sFKe6Sc0h2xJ8jGtnk1kWHOgDsN1nwSspYGKcjKaRUinEd6vlsR7WBrTWXVWuODrtDiA_7665R1etlyFy9FwWPe8Tv35T-sXlwK_YMmUC6Rv2aGEGjpyV4BB5d9i3RTb3wOMz_MIXdBrnyWquDUOsw5vz5-1Xrz6cayRT0r0G3u6NlLGGID7FB-x_4A-0n0YXtMiq60hYc5oVvA6wwFnUVvvNQRUxwq13Ym-uZZA");
            
            // Reviews resumo
            model.addAttribute("avgReview", 4.8);
            model.addAttribute("totalReviews", 125);
            // Reviews por estrelas (1-5)
            model.addAttribute("reviewStats", List.of(
                Map.of("percent", 70, "score", 5),
                Map.of("percent", 20, "score", 4),
                Map.of("percent", 5,  "score", 3),
                Map.of("percent", 3,  "score", 2),
                Map.of("percent", 2,  "score", 1)
                ));
                
                // Lista de avaliações (reviews)
                List<Map<String, Object>> reviews = List.of(
                    Map.of(
                        "author", "Liam Harper",
                        "avatarUrl", "https://lh3.googleusercontent.com/aida-public/AB6AXuCEK1srvAvSB4Ci8gigOu2xAhQiAIBLLGkVnKJ9ofXz7jZZUUSMhvmgkV8IanVIZOxZirX7D1x4ummVf7BzrP4ziTpQ-pOkrXcg40HR8CWGJtfArpyw7pq17cN_iEevutTl3IOEUzozWGdZ8DZL1paoXoNKjS_qdnXoY46vwUrQosO5VEw1RwYo0PU4XH-T9R2GLGnduYgab_i8a7KdgP9QfE_yAHKZcq3LXlVK5BoDkD4vvfX6blvQqGtwtkurLzAF3oRBA5BMago",
                        "date", "15/06/2024",
                        "stars", 5,
                        "comment", "Ótima furadeira, funcionou perfeitamente para meu projeto. Emily foi muito atenciosa.",
                        "likes", 10,
                        "dislikes", 2
                        ),
                        Map.of(
                            "author", "Sophia Bennett",
                            "avatarUrl", "https://lh3.googleusercontent.com/aida-public/AB6AXuDC4EjL5rOLBbLM-18frnsb3BxiRsGl515VN_lixb9Qvs0SJ0q3UGJKMywxRqSJA19eLkQ_Tos1TvOjvxUE37xojy-NnVPmmMLP-vQWCCPGIh7PwN26Q0VMEEmryvrXn-av1wOXYCyBurQ755EsqLhKlKvcMmcBhh7DiP02soM52zEEbsrWMF7PNAD8RAEk_p1YuTxex2VATKYIfpSSLtsLEvc3LOkYG9r49kTvqMMf1QiGDfd6L9KN7el9Qk42deC6dvRAPhYqhjc",
                            "date", "22/05/2024",
                            "stars", 4,
                            "comment", "A furadeira estava em boas condições e fácil de usar. Só achei que uma das baterias durou menos do que o esperado.",
                            "likes", 5,
                            "dislikes", 1
                            )
                            );
                            model.addAttribute("reviews", reviews);
        */

        Tool tool = toolService.findById(id);
        if (tool == null) {
            return "redirect:/tool/list";
        }
        model.addAttribute("tool", tool);

        return "tool/toolDetail.html";
    }

    // Exibe formulário de cadastro
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("tool", new Tool());
        return "tool/createTool";
    }

    // Salva nova ferramenta
    @PostMapping("/create")
    public String createTool(@ModelAttribute Tool tool) {
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

    @GetMapping(path = "/list")
       /* @ResponseBody
    public List<Map<String, Object>> listTools() {
        List<Map<String, Object>> tools = new ArrayList<>();
        
        // Mock de ferramentas
        for (int i = 1; i <= 5; i++) {
            Map<String, Object> tool = new HashMap<>();
            tool.put("id", i);
            tool.put("name", "Furadeira " + i);
            tool.put("description", "Descrição da furadeira " + i);
            tool.put("price", 25.0 * i);
            tool.put("imageUrl", "https://example.com/tool" + i + ".jpg");
            tools.add(tool);
        }
        
        return tools;
    }*/
    public String listTools(Model model) {
        model.addAttribute("tools", toolService.listarFerramentas());
        return "tool/toolDetailHome";
    }

    @GetMapping(path = "/search")
    @ResponseBody
    public List<Map<String, Object>> searchTools(@RequestParam String query) {
        List<Map<String, Object>> tools = new ArrayList<>();
        
        // Mock de ferramentas filtradas pela query
        for (int i = 1; i <= 5; i++) {
            if (("Furadeira " + i).toLowerCase().contains(query.toLowerCase())) {
                Map<String, Object> tool = new HashMap<>();
                tool.put("id", i);
                tool.put("name", "Furadeira " + i);
                tool.put("description", "Descrição da furadeira " + i);
                tool.put("price", 25.0 * i);
                tool.put("imageUrl", "https://example.com/tool" + i + ".jpg");
                tools.add(tool);
            }
        }
        
        return tools;
    }

    @GetMapping(path = "/user/{userId}")
    public String getUserTools(@PathVariable("userId") String userId, Model model) {
        model.addAttribute("userId", userId);
        model.addAttribute("userName", "Usuário " + userId);
        model.addAttribute("tools", listTools()); // Mock de ferramentas do usuário
        return "userToolsHome.html";
    }


    
}
