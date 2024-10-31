package com.br.fatec.rotamemorias.repository;

import com.br.fatec.rotamemorias.model.Cemiterio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CemiterioRepository extends JpaRepository<Cemiterio, Long> {
}
