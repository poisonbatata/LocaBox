package br.edu.iff.ccc.locabox.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.edu.iff.ccc.locabox.entities.Rental;

public interface RentalRepository extends JpaRepository<Rental, Long> {
    List<Rental> findByStatus(Rental.Status status);
    List<Rental> findByRenter_Id(Long renterId);
    List<Rental> findByTool_Id(Long toolId);


    @Query("SELECT r FROM Rental r WHERE r.startDate <= :end AND r.endDate >= :start")
    List<Rental> findByDateBetweenStartAndEnd(LocalDate startDate, LocalDate endDate);

    // Existe conflito de aluguel para a mesma ferramenta no período?
    @Query("SELECT COUNT(r) > 0 FROM Rental r WHERE r.tool.id = :toolId AND r.startDate <= :endDate AND r.endDate >= :startDate")
    boolean existsOverlapForTool( Long toolId, LocalDate startDate, LocalDate endDate); 
    
    
    // Mesmo que o anterior, mas ignorando um rental específico (para UPDATE)
    @Query("SELECT COUNT(r) > 0 FROM Rental r WHERE r.tool.id = :toolId AND r.id <> :excludeId AND r.startDate <= :endDate AND r.endDate >= :startDate")
    boolean existsOverlapForToolExcluding( Long toolId, LocalDate startDate, LocalDate endDate, Long excludeId); 

}