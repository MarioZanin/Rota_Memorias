package com.br.fatec.rotamemorias.service;

import java.util.List;

public interface AbstractService<T> {

    T save(T entity);

    T update(T entity);

    boolean delete(Long id);

    List<T> findAll();

    T findById(Long id);
}
