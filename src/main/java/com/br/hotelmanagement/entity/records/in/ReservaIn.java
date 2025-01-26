package com.br.hotelmanagement.entity.records.in;

import com.br.hotelmanagement.domain.HospedeDomain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ReservaIn(
        HospedeDomain hospede,
        LocalDateTime checkIn,
        LocalDateTime checkOut,
        boolean estacionamento,
        BigDecimal valorTotal
) {
}
