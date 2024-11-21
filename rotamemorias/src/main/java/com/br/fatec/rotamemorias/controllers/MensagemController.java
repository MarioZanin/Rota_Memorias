package com.br.fatec.rotamemorias.controllers;

import com.br.fatec.rotamemorias.dto.MensagemDTO;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/api/mensagens")
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
        return ResponseEntity.ok(mensagemService.findAll());
    }

    @GetMapping(value = "/paginated", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna a lista paginada das mensagens", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(mediaType = "application/json"))
    })
    @Operation(summary = "Retorna a lista paginada das mensagens", description = "Obtem Lista de Mensagens com todas as informações paginadas")
    public ResponseEntity<Page<Mensagem>> getAllPaginated(Pageable pageable) {
        return ResponseEntity.ok(mensagemService.findAll(pageable));
    }

    @Override
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Mensagem> get(@PathVariable("id") Long id) {
        Mensagem mensagem = mensagemService.findById(id);
        return mensagem != null ? ResponseEntity.ok(mensagem) : ResponseEntity.notFound().build();
    }

    @Override
    @PostMapping
@Operation(summary = "Cria uma mensagem")
public ResponseEntity<Mensagem> post(@RequestBody @Valid MensagemDTO mensagemDTO) {
    try {
        // Verificação do Falecido
        Falecido falecidoBuscado = falecidoService.findById(mensagemDTO.getFalecidoId());
        if (falecidoBuscado == null) {
            return ResponseEntity.badRequest().body(null);  // Retorna BadRequest se falecido não encontrado
        }

        // Criação da nova Mensagem
        Mensagem novaMensagem = new Mensagem();
        novaMensagem.setMensagem(mensagemDTO.getMensagem());
        novaMensagem.setNomeRemetente(mensagemDTO.getNomeRemetente());
        novaMensagem.setDataEnvio(mensagemDTO.getDataEnvio());
        novaMensagem.setFalecido(falecidoBuscado);

        // Salvamento da mensagem
        Mensagem createdMensagem = mensagemService.save(novaMensagem);

        // Retorno da mensagem criada
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMensagem);
    } catch (Exception e) {
        // Tratamento de erro mais detalhado
        e.printStackTrace();  // Imprimir o erro completo no log
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body(null);  // Retornar erro genérico
    }
}



    @Override
    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Exclui uma mensagem")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        if (mensagemService.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<Boolean> put(@Valid Mensagem entity) {
        throw new UnsupportedOperationException("Método PUT não implementado.");
    }

    @Override
    public ResponseEntity<Mensagem> patch(Long id, @Valid Mensagem entity) {
        throw new UnsupportedOperationException("Método PATCH não implementado.");
    }

    @PostMapping("/falecido/mensagens")
    public ResponseEntity<?> getMensagensByFalecidoId(@RequestBody Map<String, Long> request) {
        Long falecidoId = request.get("falecidoId");

        if (falecidoId == null) {
            return ResponseEntity.badRequest().body("ID do falecido não foi fornecido.");
        }

        Falecido falecido = falecidoService.findById(falecidoId);
        if (falecido == null) {
            return ResponseEntity.notFound().build();
        }

        List<Mensagem> mensagens = mensagemService.findByFalecidoId(falecidoId);

        Map<String, Object> response = new HashMap<>();
        response.put("deceasedInfo", falecido);
        response.put("messages", mensagens);

        return ResponseEntity.ok(response);
    }
}
