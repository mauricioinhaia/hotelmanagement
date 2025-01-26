package com.br.hotelmanagement.validator;

import com.br.hotelmanagement.entity.records.in.ReservaIn;
import com.br.hotelmanagement.exception.PayloadException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ReservaValidator {

    public static ReservaValidator inicializa() {
        return new ReservaValidator();
    }

    public void validarReservaIn(ReservaIn reservaIn) {
        Map<String, Object> erros = new HashMap<>();

        if (Objects.isNull(reservaIn.hospede())) {
            erros.put("hospede", "Hospede não pode ser nulo");
        }

        if (Objects.isNull(reservaIn.checkIn())) {
            erros.put("checkIn", "O checkIn não pode ser nulo");
        } else if (reservaIn.checkIn().isBefore(LocalDateTime.now())) {
            erros.put("checkIn", "A data de checkIn não pode ser anterior a data atual");
        }

        if (Objects.nonNull(reservaIn.checkOut()) && reservaIn.checkOut().isBefore(reservaIn.checkIn())) {
            erros.put("checkOut", "A data de checkOut não pode ser anterior a data de checkIn");
        }

        if (!erros.isEmpty()) {
            throw new PayloadException("Campos incorretos", this.getClass().getSimpleName(), erros);
        }
    }
}
