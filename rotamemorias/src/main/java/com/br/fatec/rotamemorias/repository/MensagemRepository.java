package com.br.fatec.rotamemorias.repository;

import com.br.fatec.rotamemorias.model.Mensagem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MensagemRepository extends JpaRepository<Mensagem, Long> {
// Método para buscar mensagens pelo ID do falecido
List<Mensagem> findByFalecidoId(Long falecidoId);
}
