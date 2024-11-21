package com.br.fatec.rotamemorias.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

import jakarta.validation.constraints.NotNull;

@Getter
@Setter
public class MensagemDTO {
    
    @NotNull
    private String nomeRemetente;
    @NotNull
    private String mensagem;
    @NotNull
    private Date  dataEnvio;
    @NotNull
    private Long  falecidoId;
}
