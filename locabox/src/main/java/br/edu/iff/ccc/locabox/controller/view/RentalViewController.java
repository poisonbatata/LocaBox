package br.edu.iff.ccc.locabox.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/rental")
public class RentalViewController {
    // CONTROLLERS DO ALUGEL
    /* Preciso:
        - Solicitar aluguel de uma ferramenta (como usuário que quer alugar)
        - Aprovar ou rejeitar solicitação de aluguel (como usuário que possui a ferramenta)

     */

    @PostMapping(path = "/rental/{toolId}")
    @ResponseBody
    public String requestRental(
            @PathVariable("toolId") String toolId,
            @RequestParam String userId,
            @RequestParam int days) {
        System.out.println("Solicitação de aluguel:");
        System.out.println("ID da ferramenta: " + toolId);
        System.out.println("ID do usuário: " + userId);
        System.out.println("Dias solicitados: " + days);

        return "Solicitação de aluguel enviada com sucesso para a ferramenta ID: " + toolId + " por " + days + " dias.";
    }


    @PostMapping(path = "/rental/approve") // Essa rota é usada pelo dono que possui a ferramenta. Ele deve poder aprovar ou rejeitar a solicitação de aluguel. 
    @ResponseBody
    public String approveRental(
            @RequestParam String rentalId,
            @RequestParam String userId) {
        System.out.println("Aprovação de aluguel:");
        System.out.println("ID do aluguel: " + rentalId);
        System.out.println("ID do usuário: " + userId);

        return "Aluguel aprovado com sucesso para o usuário ID: " + userId + " no aluguel ID: " + rentalId;
    }

}
