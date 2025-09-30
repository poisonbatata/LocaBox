package br.edu.iff.ccc.locabox.services;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.edu.iff.ccc.locabox.dto.RentalRequestDTO;
import br.edu.iff.ccc.locabox.dto.RentalResponseDTO;
import br.edu.iff.ccc.locabox.dto.ToolResponseDTO;
import br.edu.iff.ccc.locabox.dto.UserSystemResponseDTO;
import br.edu.iff.ccc.locabox.entities.Rental;
import br.edu.iff.ccc.locabox.entities.Tool;
import br.edu.iff.ccc.locabox.entities.UserSystem;
import br.edu.iff.ccc.locabox.exception.RentalNotAvaible;
import br.edu.iff.ccc.locabox.exception.RentalNotExist;
import br.edu.iff.ccc.locabox.exception.RentalStartInPast;
import br.edu.iff.ccc.locabox.exception.ToolNotExist;
import br.edu.iff.ccc.locabox.exception.UserNotExist;
import br.edu.iff.ccc.locabox.repository.RentalRepository;
import br.edu.iff.ccc.locabox.repository.ToolRepository;
import br.edu.iff.ccc.locabox.repository.UserSystemRepository;

@Service
public class RentalService {

    private final RentalRepository rentalRepository;
    private final ToolRepository toolRepository;
    private final UserSystemRepository userRepository;

    public RentalService(RentalRepository rentalRepository,
                         ToolRepository toolRepository,
                         UserSystemRepository userRepository) {
        this.rentalRepository = rentalRepository;
        this.toolRepository = toolRepository;
        this.userRepository = userRepository;
    }


        private RentalResponseDTO toDTO(Rental rental) {
        UserSystem owner = rental.getTool().getOwner();
        UserSystemResponseDTO ownerDTO = null;
        if (owner != null) {
            ownerDTO = new UserSystemResponseDTO(owner.getId(), owner.getNome(), owner.getEmail(), owner.getStatus(), owner.getRole());
        }

        ToolResponseDTO toolDTO = new ToolResponseDTO(
            rental.getTool().getId(), rental.getTool().getNome(), rental.getTool().getDescricao(),
            rental.getTool().getCategoria(), rental.getTool().getPreco(), rental.getTool().getCondicao(),
            rental.getTool().getDisponibilidade(), ownerDTO
        );

        UserSystemResponseDTO renterDTO = new UserSystemResponseDTO(
            rental.getRenter().getId(), rental.getRenter().getNome(), rental.getRenter().getEmail(),
            rental.getRenter().getStatus(), rental.getRenter().getRole()
        );

        return new RentalResponseDTO(rental.getId(), rental.getStartDate(), rental.getEndDate(), rental.getStatus(), toolDTO, renterDTO);
    }

    public List<RentalResponseDTO> findAll() {
        return rentalRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public RentalResponseDTO findByIdAsDTO(Long id) {
        Rental rental = rentalRepository.findById(id).orElseThrow(() -> new RentalNotExist(id));
        return toDTO(rental);
    }



    public Rental findById(Long id) {
        return rentalRepository.findById(id).orElseThrow(() -> new RentalNotExist(id));
    }

    public List<RentalResponseDTO> findByStatus(Rental.Status status) {
        return rentalRepository.findByStatus(status).stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<RentalResponseDTO> findByRenter(Long renterId) {
        return rentalRepository.findByRenter_Id(renterId).stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<RentalResponseDTO> findByTool(Long toolId) {
        return rentalRepository.findByTool_Id(toolId).stream().map(this::toDTO).collect(Collectors.toList());
    }

    public RentalResponseDTO create(RentalRequestDTO dto) {
        if (dto.getStartDate().isBefore(LocalDate.now())) {
            throw new RentalStartInPast(dto.getStartDate());
        }
        Tool tool = toolRepository.findById(dto.getToolId())
            .orElseThrow(() -> new ToolNotExist(dto.getToolId()));
        UserSystem renter = userRepository.findById(dto.getRenterId())
            .orElseThrow(() -> new UserNotExist(dto.getRenterId()));

        if (dto.getEndDate().isBefore(dto.getStartDate())) {
            throw new IllegalArgumentException("Data fim não pode ser antes da data início");
        }

        boolean conflict = rentalRepository.existsOverlapForTool(
            dto.getToolId(), dto.getStartDate(), dto.getEndDate());
        if (conflict) {
            throw new RentalNotAvaible(dto.getToolId(), dto.getStartDate(), dto.getEndDate());
        }

        Rental rental = new Rental();
        rental.setTool(tool);
        rental.setRenter(renter);
        rental.setStartDate(dto.getStartDate());
        rental.setEndDate(dto.getEndDate());
        rental.setStatus(Rental.Status.PENDENTE);
        return toDTO(rentalRepository.save(rental));
    }

    public RentalResponseDTO update(Long id, RentalRequestDTO dto) {
        Rental existing = findById(id);

        if (dto.getEndDate().isBefore(dto.getStartDate())) {
            throw new IllegalArgumentException("Data fim não pode ser antes da data início");
        }

        Long newToolId = existing.getTool().getId();
        if (!existing.getTool().getId().equals(dto.getToolId())) {
            Tool tool = toolRepository.findById(dto.getToolId())
                .orElseThrow(() -> new ToolNotExist(dto.getToolId()));
            existing.setTool(tool);
            newToolId = tool.getId();
        }
        if (!existing.getRenter().getId().equals(dto.getRenterId())) {
            UserSystem renter = userRepository.findById(dto.getRenterId())
                .orElseThrow(() -> new UserNotExist(dto.getRenterId()));
            existing.setRenter(renter);
        }

        boolean conflict = rentalRepository.existsOverlapForToolExcluding(
            newToolId, dto.getStartDate(), dto.getEndDate(), existing.getId());
        if (conflict) {
            throw new RentalNotAvaible(newToolId, dto.getStartDate(), dto.getEndDate());
        }

        existing.setStartDate(dto.getStartDate());
        existing.setEndDate(dto.getEndDate());
        return toDTO(rentalRepository.save(existing));
    }

    public RentalResponseDTO updateStatus(Long id, Rental.Status status) {
        Rental existing = findById(id);
        existing.setStatus(status);
        return toDTO(rentalRepository.save(existing));
    }

    public boolean deleteById(Long id) {
        if (!rentalRepository.existsById(id)) return false;
        rentalRepository.deleteById(id);
        return true;
    }
}