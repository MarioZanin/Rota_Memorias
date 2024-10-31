package com.br.fatec.rotamemorias.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class MensagemDTO {

    private String nomeRemetente;
    private String mensagem;
    private Date dataEnvio;
    private Long falecidoId;
}
