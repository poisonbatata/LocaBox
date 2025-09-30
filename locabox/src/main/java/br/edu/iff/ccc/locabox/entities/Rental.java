package br.edu.iff.ccc.locabox.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "rental", indexes = {
    @Index(name = "idx_rental_tool", columnList = "tool_id"),
    @Index(name = "idx_rental_status", columnList = "status")
})
public class Rental {

    public enum Status { PENDENTE, ACEITA, REJEITADA, EM_ANDAMENTO, CONCLUIDA, CANCELADA }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Ferramenta alugada
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tool_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_rental_tool"))
    private Tool tool;

    // Usuário que alugou (UserSystem)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "renter_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_rental_renter"))
    private UserSystem renter;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Status status = Status.PENDENTE;

    public Rental() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Tool getTool() { return tool; }
    public void setTool(Tool tool) { this.tool = tool; }

    public UserSystem getRenter() { return renter; }
    public void setRenter(UserSystem renter) { this.renter = renter; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
}
