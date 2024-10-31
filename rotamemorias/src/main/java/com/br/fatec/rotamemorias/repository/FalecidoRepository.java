package com.br.fatec.rotamemorias.repository;

import com.br.fatec.rotamemorias.model.Falecido;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FalecidoRepository extends JpaRepository<Falecido, Long> {

    // Busca falecido por nome
    @Query("SELECT f FROM Falecido f WHERE LOWER(f.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    List<Falecido> findFalecidoByNome(String nome);

    // Busca falecido por data de falecimento
    @Query("SELECT f FROM Falecido f WHERE f.dataFalecimento = :dataFalecimento")
    List<Falecido> findFalecidoByDataFalecimento(LocalDate dataFalecimento);

    // Busca falecidos por nome da mãe
    @Query("SELECT f FROM Falecido f WHERE LOWER(f.nomeMae) LIKE LOWER(CONCAT('%', :nomeMae, '%'))")
    List<Falecido> findFalecidoByNomeMae(String nomeMae);

    // Busca falecidos por nome e data de falecimento
    @Query("SELECT f FROM Falecido f WHERE LOWER(f.nome) LIKE LOWER(CONCAT('%', :nome, '%')) AND f.dataFalecimento = :dataFalecimento")
    List<Falecido> findFalecidoByNomeAndDataFalecimento(String nome, LocalDate dataFalecimento);
    @Query("SELECT f FROM Falecido f " +
           "LEFT JOIN f.cemiterio c " +  // Join opcional com o cemitério
           "WHERE (:nome IS NULL OR LOWER(f.nome) LIKE LOWER(CONCAT('%', :nome, '%'))) " +
           "AND (:nomeCemiterio IS NULL OR LOWER(c.nome) LIKE LOWER(CONCAT('%', :nomeCemiterio, '%'))) " +
           "AND (:dataFalecimento IS NULL OR f.dataFalecimento = :dataFalecimento) " +
           "AND (:nomeMae IS NULL OR LOWER(f.nomeMae) LIKE LOWER(CONCAT('%', :nomeMae, '%')))")
 

List<Falecido> findByCriteria(@Param("nome") String nome,
                          @Param("nomeCemiterio") String nomeCemiterio,
                          @Param("dataFalecimento") Date date,
                          @Param("nomeMae") String nomeMae);


}
