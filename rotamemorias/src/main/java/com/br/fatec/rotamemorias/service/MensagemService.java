package com.br.fatec.rotamemorias.service;

import com.br.fatec.rotamemorias.model.Mensagem;
import com.br.fatec.rotamemorias.repository.MensagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return mensagemRepository.findById(id).orElse(null);
    }

    public Mensagem save(Mensagem mensagem) {
        if (mensagem.getFalecido() == null || mensagem.getFalecido().getId() == null) {
            throw new IllegalArgumentException("Falecido inválido ou não informado.");
        }
        return mensagemRepository.save(mensagem);
    }


    public boolean delete(Long id) {
        if (mensagemRepository.existsById(id)) {
            mensagemRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Mensagem> findByFalecidoId(Long falecidoId) {
        return mensagemRepository.findByFalecidoId(falecidoId);
    }
}
