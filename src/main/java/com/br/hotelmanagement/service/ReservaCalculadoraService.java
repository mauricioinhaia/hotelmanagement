package com.br.hotelmanagement.service;

import com.br.hotelmanagement.dataaccess.query.ReservaDomainQueryDataAccess;
import com.br.hotelmanagement.entity.records.out.ReservaComValoresOut;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ReservaCalculadoraService {

    private final ReservaDomainQueryDataAccess reservaDomainQueryDataAccess;

    public ReservaCalculadoraService(ReservaDomainQueryDataAccess reservaDomainQueryDataAccess) {
        this.reservaDomainQueryDataAccess = reservaDomainQueryDataAccess;
    }

    public ReservaComValoresOut calcularValoresReservas(ReservaComValoresOut reservaComValoresOut, Long idHospede) {
        BigDecimal valorTotalReservas = this.reservaDomainQueryDataAccess.somarValoresReservas(idHospede);
        BigDecimal valorUltimaReserva = this.reservaDomainQueryDataAccess.valorUltimaReserva(idHospede);

        return reservaComValoresOut.withValorReservas(valorTotalReservas, valorUltimaReserva);
    }
}
