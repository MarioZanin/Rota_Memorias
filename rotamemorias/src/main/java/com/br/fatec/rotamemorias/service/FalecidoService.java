package com.br.fatec.rotamemorias.service;


import com.br.fatec.rotamemorias.dto.SearchDTO;
import com.br.fatec.rotamemorias.model.Falecido;
import com.br.fatec.rotamemorias.repository.FalecidoRepository;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FalecidoService implements FalecidoServiceInterface {
    
    
    
    private static final Long FalecidoId = null;
    @Autowired
    private FalecidoRepository falecidoRepository;



    @Override
    public List<Falecido> search(SearchDTO searchDTO) {
        return falecidoRepository.findByCriteria(
            searchDTO.getNome(),
            searchDTO.getNomeCemiterio(),
            searchDTO.getDataFalecimento(),
            searchDTO.getNomeMae()
        );
    }
   
    @Override
    public Falecido save(Falecido entity) {
        return falecidoRepository.save(entity);
    }

    @Override
    public Falecido update(Falecido entity) {
        return falecidoRepository.save(entity);
    }

    @Override
    public boolean delete(Long id) {
        falecidoRepository.deleteById(id);
        return false;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<Falecido> findAll() {
        return (Page<Falecido>) falecidoRepository.findAll();
    }

    public Falecido updatePartial(@Valid Falecido falecido) {
        throw new UnsupportedOperationException("Unimplemented method 'updatePartial'");
    }

    @Override
    public Falecido findById(Long id) {
        return falecidoRepository.findById(FalecidoId).orElse(null);
    }
}
