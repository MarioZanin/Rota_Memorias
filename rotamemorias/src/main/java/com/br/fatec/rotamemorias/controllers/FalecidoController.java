package com.br.fatec.rotamemorias.controllers;

import com.br.fatec.rotamemorias.dto.MensagemDTO;
import com.br.fatec.rotamemorias.dto.SearchDTO;
import com.br.fatec.rotamemorias.model.Falecido;
import com.br.fatec.rotamemorias.model.Mensagem;
import com.br.fatec.rotamemorias.service.FalecidoService;
import com.br.fatec.rotamemorias.service.FalecidoServiceInterface;

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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.sql.Date;
import java.util.List;



@RestController
@RequestMapping("/api/falecido")
@Tag(name = "Falecidos", description = "Gerenciar Falecidos")
public class FalecidoController implements IController<Falecido> {

    @Autowired
    private FalecidoServiceInterface falecidoService;



    @GetMapping("/search")
@Operation(summary = "Busca falecidos")
public ResponseEntity<List<Falecido>> search(
    @RequestParam(value = "nome", required = true) String nome,
    @RequestParam(value = "nomeCemiterio", required = false) String nomeCemiterio,
    @RequestParam(value = "dataFalecimento", required = false) Date dataFalecimento,
    @RequestParam(value = "nomeMae", required = false) String nomeMae) {

    SearchDTO searchDTO = new SearchDTO();
    searchDTO.setNome(nome);
    searchDTO.setNomeCemiterio(nomeCemiterio);
    searchDTO.setDataFalecimento(dataFalecimento);
    searchDTO.setNomeMae(nomeMae);

    List<Falecido> results = falecidoService.search(searchDTO);
    return ResponseEntity.ok(results);
}

    
    @Override
    @GetMapping(produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna a lista dos falecidos", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(mediaType = "application/json"))
    })
    @Operation(summary = "Retorna a lista dos falecidos", description = "Obtem Lista de Falecidos com todas as informações")
    public ResponseEntity getAll() {
        @SuppressWarnings("unchecked")
        List<Falecido> falecidos = (List<Falecido>) ((FalecidoService) falecidoService).findAll();
        return ResponseEntity.ok(falecidos);
    }

    @GetMapping(value = "/paginated", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna a lista paginada dos falecidos", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(mediaType = "application/json"))
    })
    @Operation(summary = "Retorna a lista paginada dos falecidos", description = "Obtem Lista de Falecidos com todas as informações paginadas")
    public ResponseEntity<Page<Falecido>> getAllPaginated(Pageable pageable) {
        Page<Falecido> falecidos = (Page<Falecido>) falecidoService.findAll();
        return ResponseEntity.ok(falecidos);
    }

    @Override
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Falecido> get(@PathVariable("id") Long id) {
        Falecido falecido = falecidoService.findById(id);
        if (falecido != null) {
            return ResponseEntity.ok(falecido);
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    @PostMapping
    @Operation(summary = "Cria um falecido")
    public ResponseEntity<Falecido> post(@RequestBody @Valid Falecido falecido) {
        Falecido createdFalecido = falecidoService.save(falecido);
        URI location = URI.create(String.format("/api/falecido/%s", createdFalecido.getId()));
        return ResponseEntity.created(location).body(createdFalecido);
    }

    @Override
    @PutMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Falecido atualizado com sucesso", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Falecido não encontrado")
    })
    
    @Operation(summary = "Atualiza um falecido")
    public ResponseEntity<Boolean> put(@RequestBody @Valid Falecido falecido) {
        boolean updatedFalecido = falecidoService.update(falecido) != null;
        if (updatedFalecido) {
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    @PatchMapping("/{id}")
    @Operation(summary = "Atualiza parcialmente um falecido")
    public ResponseEntity<Falecido> patch(@PathVariable("id") Long id, @RequestBody @Valid Falecido falecido) {
        falecido.setId(id);
        Falecido patchedFalecido = falecidoService.updatePartial(falecido);
        if (patchedFalecido != null) {
            return ResponseEntity.ok(patchedFalecido);
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Exclui um falecido")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        boolean deleted = (boolean) falecidoService.delete(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }


    @Override
    public ResponseEntity<Mensagem> post(@Valid MensagemDTO entity) {
        
        throw new UnsupportedOperationException("Unimplemented method 'post'");
    }
}