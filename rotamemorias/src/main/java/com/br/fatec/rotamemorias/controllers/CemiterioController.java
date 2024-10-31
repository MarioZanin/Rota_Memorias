package com.br.fatec.rotamemorias.controllers;

import com.br.fatec.rotamemorias.model.Cemiterio;
import com.br.fatec.rotamemorias.service.CemiterioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/cemiterio")
@Tag(name = "Cemitérios", description = "Gerenciar Cemitérios")
public class CemiterioController implements IController<Cemiterio> {

    @Autowired
    private CemiterioService cemiterioService;

    @Override
    @GetMapping(produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna a lista dos cemitérios", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(mediaType = "application/json"))
    })
    @Operation(summary = "Retorna a lista dos cemitérios", description = "Obtem Lista de Cemitérios com todas as informações")
    public ResponseEntity<List<Cemiterio>> getAll() {
        List<Cemiterio> cemiterios = cemiterioService.findAll();
        return ResponseEntity.ok(cemiterios);
    }

    @GetMapping(value = "/paginated", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna a lista paginada dos cemitérios", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(mediaType = "application/json"))
    })
    @Operation(summary = "Retorna a lista paginada dos cemitérios", description = "Obtem Lista de Cemitérios com todas as informações paginadas")
    public ResponseEntity<Page<Cemiterio>> getAllPaginated(Pageable pageable) {
        @SuppressWarnings("unchecked")
        Page<Cemiterio> cemiterios = (Page<Cemiterio>) cemiterioService.findAll();
        return ResponseEntity.ok(cemiterios);
    }

    @Override
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Cemiterio> get(@PathVariable("id") Long id) {
        Cemiterio cemiterio = cemiterioService.findById(id);
        if (cemiterio != null) {
            return ResponseEntity.ok(cemiterio);
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    @PostMapping
    @Operation(summary = "Cria um cemitério")
    public ResponseEntity<Cemiterio> post(@RequestBody @Valid Cemiterio cemiterio) {
        Cemiterio createdCemiterio = cemiterioService.save(cemiterio);
        URI location = URI.create(String.format("/api/cemiterio/%s", createdCemiterio.getId()));
        return ResponseEntity.created(location).body(createdCemiterio);
    }

    @Override
    @PutMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cemitério atualizado com sucesso", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Cemitério não encontrado")
    })
    @Operation(summary = "Atualiza um cemitério")
    public ResponseEntity<Boolean> put(@RequestBody @Valid Cemiterio cemiterio) {
        boolean updatedCemiterio = cemiterioService.update(cemiterio) != null;
        if (updatedCemiterio) {
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    @PatchMapping("/{id}")
    @Operation(summary = "Atualiza parcialmente um cemitério")
    public ResponseEntity<Cemiterio> patch(@PathVariable("id") Long id, @RequestBody @Valid Cemiterio cemiterio) {
        cemiterio.setId(id);
        Cemiterio patchedCemiterio = cemiterioService.updatePartial(cemiterio);
        if (patchedCemiterio != null) {
            return ResponseEntity.ok(patchedCemiterio);
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Exclui um cemitério")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        boolean deleted = cemiterioService.delete(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/cemiterio/{id}")
    public ResponseEntity<Cemiterio> getCemiterioById(@PathVariable Long id) {
        Cemiterio cemiterio = cemiterioService.findById(id);
        if (cemiterio == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cemiterio);
    }
}
