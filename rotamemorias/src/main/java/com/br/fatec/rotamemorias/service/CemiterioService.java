package com.br.fatec.rotamemorias.service;

import com.br.fatec.rotamemorias.model.Cemiterio;
import com.br.fatec.rotamemorias.repository.CemiterioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CemiterioService implements AbstractService<Cemiterio> {

    @Autowired
    private CemiterioRepository cemiterioRepository;

    @Override
    public Cemiterio save(Cemiterio entity) {
        return cemiterioRepository.save(entity);
    }

    @Override
    public Cemiterio update(Cemiterio entity) {
        return cemiterioRepository.save(entity);
    }

    @Override
    public boolean delete(Long id) {
        cemiterioRepository.deleteById(id);
        return false;
    }

    @Override
    public List<Cemiterio> findAll() {
        return cemiterioRepository.findAll();
    }

    @Override
    public Cemiterio findById(Long id) {
        return cemiterioRepository.findById(id).orElse(null);
    }

    public Cemiterio create(Cemiterio cemiterio) {
        //Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

    public Cemiterio updatePartial(@Valid Cemiterio cemiterio) {
        throw new UnsupportedOperationException("Unimplemented method 'updatePartial'");
    }

}
