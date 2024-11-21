package com.br.fatec.rotamemorias.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class SearchDTO {
    private String nome;
    private String nomeCemiterio;
    private Date dataFalecimento;
    private String nomeMae;
}


