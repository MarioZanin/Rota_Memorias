package com.br.fatec.rotamemorias.service;

import com.br.fatec.rotamemorias.model.Falecido;

import jakarta.validation.Valid;

import com.br.fatec.rotamemorias.dto.SearchDTO;

import java.util.List;

import org.springframework.data.domain.Page;

public interface FalecidoServiceInterface {
    List<Falecido> search(SearchDTO searchDTO);

	Page<Falecido> findAll();

    Falecido findById(Long id);

    Falecido save(@Valid Falecido falecido);

    Object update(@Valid Falecido falecido);

    Falecido updatePartial(@Valid Falecido falecido);

    boolean delete(Long id);
}

