package br.edu.iff.ccc.locabox.mapper;

import br.edu.iff.ccc.locabox.entities.UserSystem;
import br.edu.iff.ccc.locabox.dto.UserProfileDTO;

import java.time.Year;

public class UserProfileMapper {

    public static UserProfileDTO toDTO(UserSystem u) {
        if (u == null) return null;
        UserProfileDTO dto = new UserProfileDTO();
        dto.setId(u.getId());
        dto.setName(u.getNome());       // entidade usa 'nome' :contentReference[oaicite:3]{index=3}
        dto.setEmail(u.getEmail());
        dto.setStatus(u.getStatus());
        dto.setRole(u.getRole());

        // Derivados/placeholder (ajuste quando tiver esses dados de verdade)
        dto.setAvatarUrl(null);                 // template aplica fallback para /images/avatar.png
        dto.setJoinedYear(Year.now().getValue());
        dto.setAvgRating(4.5);                  // placeholder
        dto.setTotalReviews(24);                // placeholder
        dto.setPhone(null);
        dto.setAddress(null);
        dto.setDescription(null);
        return dto;
    }

    private UserProfileMapper() {}
}
