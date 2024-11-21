package com.br.fatec.rotamemorias.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "cemiterio")
public class Cemiterio extends AbstractEntity {

    @Column(length = 150)
    private String foto;
    
    @Column(length = 150, nullable = false, unique = true)
    private String nome;
    
    @Column(length = 150)
    private String rua;
    
    @Column(length = 20)
    private String numero;
    
    @Column(length = 150)
    private String bairro;
    
    @Column(length = 150)
    private String cidade;
    
    @Column(length = 150)
    private String estado;
    
    @Column(name = "horarios_funcionamento", length = 250)
    private String horariosFuncionamento;
    
    @Column(name = "pagina_oficial", length = 250)
    private String paginaOficial;
    
    @Column(length = 10)
    private String telefone;
    
    @Column(name = "localizacao_cemiterio", length = 150)
    private String localizacaoCemiterio;
}