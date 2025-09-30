package br.edu.iff.ccc.locabox.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
//import jakarta.validation.constraints.FutureOrPresent;

public class RentalRequestDTO {
    @NotNull
    private Long toolId;

    @NotNull
    private Long renterId;

    @NotNull //@FutureOrPresent
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    public Long getToolId() { return toolId; }
    public Long getRenterId() { return renterId; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }

    public void setToolId(Long toolId) { this.toolId = toolId; }
    public void setRenterId(Long renterId) { this.renterId = renterId; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
}