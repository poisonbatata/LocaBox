package br.edu.iff.ccc.locabox.mapper;

import br.edu.iff.ccc.locabox.entities.Tool;
import br.edu.iff.ccc.locabox.dto.ToolCardDTO;

import java.math.BigDecimal;
import java.util.Arrays;

public class ToolCardMapper {
    private static final String PLACEHOLDER = "/images/placeholder.png";

    public static ToolCardDTO toCard(Tool t) {
        if (t == null) return null;
        ToolCardDTO dto = new ToolCardDTO();
        dto.setId(t.getId());
        dto.setTitle(t.getNome());
        dto.setPricePerDay(BigDecimal.valueOf(t.getPreco())); // sua Tool usa double
        dto.setMainImageUrl(firstImage(t.getFotos()));
        dto.setRating(0.0); // TODO: substitua pela média real de reviews
        return dto;
    }

    private static String firstImage(String fotos) {
        if (fotos == null || fotos.isBlank()) return PLACEHOLDER;
        String[] parts = Arrays.stream(fotos.split("[,;\\s]+"))
                .filter(s -> !s.isBlank()).toArray(String[]::new);
        return parts.length > 0 ? parts[0] : PLACEHOLDER;
    }
}
