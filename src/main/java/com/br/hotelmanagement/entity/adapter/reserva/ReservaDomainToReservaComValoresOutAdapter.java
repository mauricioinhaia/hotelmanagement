package com.br.hotelmanagement.entity.adapter.reserva;

import com.br.hotelmanagement.domain.ReservaDomain;
import com.br.hotelmanagement.entity.records.out.ReservaComValoresOut;

public class ReservaDomainToReservaComValoresOutAdapter {

    public static ReservaDomainToReservaComValoresOutAdapter inicializa() {
        return new ReservaDomainToReservaComValoresOutAdapter();
    }

    public ReservaComValoresOut converte(ReservaDomain reservaDomain) {
        return new ReservaComValoresOut(
                reservaDomain.getId(),
                reservaDomain.getHospede(),
                reservaDomain.getCheckIn(),
                reservaDomain.getCheckOut(),
                reservaDomain.isEstacionamento(),
                reservaDomain.getStatus(),
                reservaDomain.getValorTotal(),
                null,
                null
        );
    }
}
