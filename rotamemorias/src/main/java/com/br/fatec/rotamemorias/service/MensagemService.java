package com.br.fatec.rotamemorias.service;

import com.br.fatec.rotamemorias.model.Mensagem;
import com.br.fatec.rotamemorias.repository.MensagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MensagemService {

    @Autowired
    private MensagemRepository mensagemRepository;

    public List<Mensagem> findAll() {
        return mensagemRepository.findAll();
    }

    public Page<Mensagem> findAll(Pageable pageable) {
        return mensagemRepository.findAll(pageable);
    }

    public Mensagem findById(Long id) {
        Optional<Mensagem> mensagem = mensagemRepository.findById(id);
        return mensagem.orElse(null);
    }

    public Mensagem save(Mensagem mensagem) {
        return mensagemRepository.save(mensagem);
    }

    public boolean update(Mensagem mensagem) {
        if (mensagemRepository.existsById(mensagem.getId())) {
            mensagemRepository.save(mensagem);
            return true;
        }
        return false;
    }

    public Mensagem updatePartial(Mensagem mensagem) {
        Optional<Mensagem> existingMensagem = mensagemRepository.findById(mensagem.getId());
        if (existingMensagem.isPresent()) {
            Mensagem currentMensagem = existingMensagem.get();
            // Atualizar campos conforme necessário
            // Exemplo: currentMensagem.setCampo(mensagem.getCampo());
            return mensagemRepository.save(currentMensagem);
        }
        return null;
    }

    public boolean delete(Long id) {
        if (mensagemRepository.existsById(id)) {
            mensagemRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Mensagem> findByFalecidoId(Long falecidoId) {
             throw new UnsupportedOperationException("Unimplemented method 'findByFalecidoId'");
    }

    
}
