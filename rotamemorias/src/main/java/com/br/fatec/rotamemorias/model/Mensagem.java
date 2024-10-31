package com.br.fatec.rotamemorias.model;

import java.util.Date;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Mensagem extends AbstractEntity {

    @Lob
    private String mensagem;
    
    @Temporal(TemporalType.DATE)
    private Date dataEnvio;

    @ManyToOne
    @JoinColumn(name = "falecido_id")
    private Falecido falecido;
}
