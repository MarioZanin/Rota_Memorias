package com.br.fatec.rotamemorias.model;

import java.util.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Falecido extends AbstractEntity {

    @Column(length = 255)
    private String foto;
    
    @Column(length = 150, nullable = false)
    private String nome;
    
    @Temporal(TemporalType.DATE)
    private Date dataNascimento;
    
    @Temporal(TemporalType.DATE)
    private Date dataFalecimento;
    
    @Column(length = 150)
    private String nomeMae;
    
    @Column(length = 150)
    private String nomePai;
    
    @Column(length = 150)
    private String profissao;
    
    @Column(length = 150)
    private String localizacaoSepultura;
    
    @ManyToOne
    @JoinColumn(name = "cemiterio_id")
    private Cemiterio cemiterio;
}
