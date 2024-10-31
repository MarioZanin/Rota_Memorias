package com.br.fatec.rotamemorias.controllers;

import com.br.fatec.rotamemorias.model.Falecido;
import com.br.fatec.rotamemorias.model.Mensagem;
import com.br.fatec.rotamemorias.service.FalecidoService;
import com.br.fatec.rotamemorias.service.MensagemService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/mensagem")
@Tag(name = "Mensagens", description = "Gerenciar Mensagens")
public class MensagemController implements IController<Mensagem> {

    @Autowired
    private MensagemService mensagemService;

    @Autowired
    private FalecidoService falecidoService;

    @Override
    @GetMapping(produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna a lista das mensagens", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(mediaType = "application/json"))
    })
    @Operation(summary = "Retorna a lista das mensagens", description = "Obtem Lista de Mensagens com todas as informações")
    public ResponseEntity<List<Mensagem>> getAll() {
        List<Mensagem> mensagens = mensagemService.findAll();
        return ResponseEntity.ok(mensagens);
    }

    @GetMapping(value = "/paginated", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna a lista paginada das mensagens", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(mediaType = "application/json"))
    })
    @Operation(summary = "Retorna a lista paginada das mensagens", description = "Obtem Lista de Mensagens com todas as informações paginadas")
    public ResponseEntity<Page<Mensagem>> getAllPaginated(Pageable pageable) {
        Page<Mensagem> mensagens = mensagemService.findAll(pageable);
        return ResponseEntity.ok(mensagens);
    }

    @Override
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Mensagem> get(@PathVariable("id") Long id) {
        Mensagem mensagem = mensagemService.findById(id);
        if (mensagem != null) {
            return ResponseEntity.ok(mensagem);
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    @PostMapping
    @Operation(summary = "Cria uma mensagem")
    public ResponseEntity<Mensagem> post(@RequestBody @Valid Mensagem mensagem) {
        Mensagem createdMensagem = mensagemService.save(mensagem);
        URI location = URI.create(String.format("/api/mensagem/%s", createdMensagem.getId()));
        return ResponseEntity.created(location).body(createdMensagem);
    }

    @Override
    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Exclui uma mensagem")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        boolean deleted = mensagemService.delete(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<Boolean> put(@Valid Mensagem entity) {
        //Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'put'");
    }

    @Override
    public ResponseEntity<Mensagem> patch(Long id, @Valid Mensagem entity) {
        //Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'patch'");
    }

    // Novo método para buscar mensagens usando o ID do falecido
    @PostMapping("/falecido/mensagens")
    public ResponseEntity<Map<String, Object>> getMensagensByFalecidoId(@RequestBody Map<String, Long> request) {
        Long falecidoId = request.get("falecidoId");

        if (falecidoId == null) {
            return ResponseEntity.badRequest().body(null);
        }

        // Busca o falecido pelo ID
        Falecido falecido = falecidoService.findById(falecidoId);

        if (falecido == null) {
            return ResponseEntity.notFound().build();
        }

        // Busca as mensagens associadas ao ID do falecido
        List<Mensagem> mensagens = mensagemService.findByFalecidoId(falecidoId);

        // Cria o mapa de resposta com as informações do falecido e das mensagens
        Map<String, Object> response = new HashMap<>();
        response.put("deceasedInfo", falecido);
        response.put("messages", mensagens);

        return ResponseEntity.ok(response);
    }
}
