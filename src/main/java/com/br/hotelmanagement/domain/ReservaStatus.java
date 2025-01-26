package com.br.hotelmanagement.domain;

public enum ReservaStatus {
    ABERTO    ("A", "Reserva em andamento"),
    FINALIZADO("F", "Reserva finalizada"),
    CANCELADO ("C", "Reserva cancelada");

    private final String sigla;
    private final String descricao;

    ReservaStatus(String sigla, String descricao) {
        this.sigla = sigla;
        this.descricao = descricao;
    }

    public String getSigla() {
        return sigla;
    }

    public String getDescricao() {
        return descricao;
    }

    public static ReservaStatus fromSigla(String sigla) {
        for (ReservaStatus status : values()) {
            if (status.sigla.equalsIgnoreCase(sigla)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Sigla inválida: " + sigla);
    }
}
