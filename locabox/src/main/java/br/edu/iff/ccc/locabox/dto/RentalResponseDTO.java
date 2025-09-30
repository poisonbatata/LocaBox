package br.edu.iff.ccc.locabox.dto;

import br.edu.iff.ccc.locabox.entities.Rental;
import java.time.LocalDate;

public class RentalResponseDTO {
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private Rental.Status status;
    private ToolResponseDTO tool; // DTO da Ferramenta
    private UserSystemResponseDTO renter; // DTO do Locatário

    // Construtor, Getters e Setters
    public RentalResponseDTO(Long id, LocalDate startDate, LocalDate endDate, Rental.Status status, ToolResponseDTO tool, UserSystemResponseDTO renter) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.tool = tool;
        this.renter = renter;
    }
 
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Rental.Status getStatus() {
        return status;
    }

    public void setStatus(Rental.Status status) {
        this.status = status;
    }

    public ToolResponseDTO getTool() {
        return tool;
    }

    public void setTool(ToolResponseDTO tool) {
        this.tool = tool;
    }

    public UserSystemResponseDTO getRenter() {
        return renter;
    }

    public void setRenter(UserSystemResponseDTO renter) {
        this.renter = renter;
    }
}