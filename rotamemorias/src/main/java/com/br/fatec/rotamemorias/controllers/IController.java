package com.br.fatec.rotamemorias.controllers;

import org.springframework.http.ResponseEntity;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import java.util.List;

public interface IController<T> {
    ResponseEntity<List<T>> getAll();
    ResponseEntity<T> get(Long id);
    ResponseEntity<T> post(@RequestBody @Valid T entity);
    ResponseEntity<Boolean> put(@RequestBody @Valid T entity);
    ResponseEntity<T> patch(Long id, @RequestBody @Valid T entity);
    ResponseEntity<Void> delete(Long id);
}
