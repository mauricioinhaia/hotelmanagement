package com.br.hotelmanagement.entity.adapter.reserva;

import com.br.hotelmanagement.domain.ReservaDomain;
import com.br.hotelmanagement.entity.records.out.ReservaOut;

public class ReservaDomainToReservaOutAdapter {
    
    public static ReservaDomainToReservaOutAdapter inicializa() {
        return new ReservaDomainToReservaOutAdapter();
    }
    
    public ReservaOut converte(ReservaDomain reservaDomain) {
        return new ReservaOut(
                reservaDomain.getId(),
                reservaDomain.getHospede(), 
                reservaDomain.getCheckIn(),
                reservaDomain.getCheckOut(),
                reservaDomain.isEstacionamento(),
                reservaDomain.getStatus(), 
                reservaDomain.getValorTotal()
        );
    }
}
