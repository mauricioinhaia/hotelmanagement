package com.br.hotelmanagement.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "tarifa")
public class TarifaDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "descricaotarifa")
    private String descricao;

    private boolean fimdesemana;

    @Column(name = "tiposervico")
    private String tipoServico;

    @Column(name = "valor")
    private BigDecimal valor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipoServico() {
        return tipoServico;
    }

    public void setTipoServico(String tipoServico) {
        this.tipoServico = tipoServico;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public boolean isFimdesemana() {
        return fimdesemana;
    }

    public void setFimdesemana(boolean fimdesemana) {
        this.fimdesemana = fimdesemana;
    }
}
