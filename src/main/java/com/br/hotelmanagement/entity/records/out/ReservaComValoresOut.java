package com.br.hotelmanagement.entity.records.out;

import com.br.hotelmanagement.domain.HospedeDomain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ReservaComValoresOut(
        Long id,
        HospedeDomain hospede,
        LocalDateTime checkIn,
        LocalDateTime checkOut,
        boolean estacionamento,
        String status,
        BigDecimal valorTotal,
        BigDecimal valorTotalReservas,
        BigDecimal valorUltimaReserva
) {
    public ReservaComValoresOut withValorReservas(BigDecimal valorTotalReservas, BigDecimal valorUltimaReserva) {
        return new ReservaComValoresOut(
                this.id,
                this.hospede,
                this.checkIn,
                this.checkOut,
                this.estacionamento,
                this.status,
                this.valorTotal,
                valorTotalReservas,
                valorUltimaReserva
        );
    }
}
