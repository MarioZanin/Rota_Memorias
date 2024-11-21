package com.br.fatec.rotamemorias.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="mensagens")
@Getter
@Setter
public class Mensagem extends AbstractEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 
    @Lob
    @Column(length = 300)
    public String mensagem;
    
    @Column(name="nome_remetente", length = 150)
    private String nomeRemetente;

    @Temporal(TemporalType.DATE)
    @Column(name = "data_envio")
    public Date dataEnvio;

    @ManyToOne
    @JoinColumn(name = "falecido_id", nullable = false)
    public Falecido falecido;
}
