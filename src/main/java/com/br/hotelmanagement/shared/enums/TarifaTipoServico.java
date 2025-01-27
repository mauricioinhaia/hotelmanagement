package com.br.hotelmanagement.shared.enums;

public enum TarifaTipoServico {
    HOSPEDAGEM("H", "Hospedagem"),
    ESTACIONAMENTO("E", "Estacionamento");

    private final String sigla;
    private final String descricao;

    TarifaTipoServico(String sigla, String descricao) {
        this.sigla = sigla;
        this.descricao = descricao;
    }

    public String getSigla() {
        return sigla;
    }

    public String getDescricao() {
        return descricao;
    }

    public static TarifaTipoServico fromSigla(String sigla) {
        for (TarifaTipoServico status : values()) {
            if (status.sigla.equalsIgnoreCase(sigla)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Sigla inválida: " + sigla);
    }
}
