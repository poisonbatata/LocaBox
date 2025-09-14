package br.edu.iff.ccc.locabox.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.edu.iff.ccc.locabox.entities.Tool;

@Repository
public interface ToolRepository extends JpaRepository<Tool, Long> {
    Tool findByName(String name);
    
    List<Tool> findByCategoria(String Categoria);

    //List<Tool> findByQuantidadeEstoque(int quantidadeEstoque);

    @Query("SELECT t FROM Tool t WHERE t.preco BETWEEN :minPrice AND :maxPrice")
    List<Tool> findByPriceBetween(double minPrice, double maxPrice);

    @Query("SELECT t FROM Tool t WHERE t.preco <= :price ORDER BY t.preco ASC")
    List<Tool> findByPriceGreaterThanEqual(double price);

    @Query("SELECT t FROM Tool t WHERE t.preco >= :price ORDER BY t.preco DESC")
    List<Tool> findByPriceLessThanEqual(double price);

    @Query("SELECT t FROM Tool t WHERE t.condicao = :condition")
    List<Tool> findByCondition(String condition);

    @Query("SELECT t FROM Tool t WHERE t.owner.id = :ownerId")
    List<Tool> findByOwnerId(Long ownerId);

}
