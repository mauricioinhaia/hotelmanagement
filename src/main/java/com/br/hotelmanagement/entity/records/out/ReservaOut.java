package com.br.hotelmanagement.entity.records.out;

import com.br.hotelmanagement.domain.HospedeDomain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ReservaOut(
        Long id,
        HospedeDomain hospede,
        LocalDateTime checkIn,
        LocalDateTime checkOut,
        boolean estacionamento,
        String status,
        BigDecimal valorTotal
) {
}
