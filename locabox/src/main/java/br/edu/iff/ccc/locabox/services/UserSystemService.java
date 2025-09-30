package br.edu.iff.ccc.locabox.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.edu.iff.ccc.locabox.dto.UserSystemResponseDTO;
import br.edu.iff.ccc.locabox.entities.UserSystem;
import br.edu.iff.ccc.locabox.exception.UserNotExist;
import br.edu.iff.ccc.locabox.repository.UserSystemRepository;

@Service
public class UserSystemService {

    private final UserSystemRepository userRepository;

    public UserSystemService(UserSystemRepository userRepository) {
        this.userRepository = userRepository;
    }


    private UserSystemResponseDTO toDTO(UserSystem user) {
        return new UserSystemResponseDTO(user.getId(), user.getNome(), user.getEmail(), user.getStatus(), user.getRole());
    }

    // Altere os métodos públicos para retornarem DTOs
    public List<UserSystemResponseDTO> findAll() {
        return userRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public UserSystemResponseDTO findByIdAsDTO(Long id) {
        UserSystem user = userRepository.findById(id).orElseThrow(() -> new UserNotExist(id));
        return toDTO(user);
    }
    
    public UserSystem findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotExist(id));
    }


    public UserSystem findByEmail(String email) {
        UserSystem user = userRepository.findByEmail(email);
        if (user == null) throw new UserNotExist(email);
        return user;
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public UserSystem create(UserSystem user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new DataIntegrityViolationException("Email já cadastrado: " + user.getEmail());
        }
        return userRepository.save(user);
    }

    public UserSystem update(Long id, UserSystem updated) {
        UserSystem existing = userRepository.findById(id).orElseThrow(() -> new UserNotExist(id));

        if (!existing.getEmail().equalsIgnoreCase(updated.getEmail())
                && userRepository.existsByEmail(updated.getEmail())) {
            throw new DataIntegrityViolationException("Email já cadastrado: " + updated.getEmail());
        }

        existing.setNome(updated.getNome());
        existing.setEmail(updated.getEmail());
        existing.setStatus(updated.getStatus());
        existing.setRole(updated.getRole());
        return userRepository.save(existing);
    }

    public void deleteById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotExist(id);
        }
        userRepository.deleteById(id);
    }

    public List<UserSystem> searchByNome(String termo) {
        return userRepository.findByNomeContainingIgnoreCase(termo);
    }
}
