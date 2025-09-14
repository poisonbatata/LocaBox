package br.edu.iff.ccc.locabox.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.iff.ccc.locabox.entities.Tool;

@Repository
public interface ToolRepository extends JpaRepository<Tool, Long> {

}
