package com.br.hotelmanagement.entity.adapter.reserva;

import com.br.hotelmanagement.domain.ReservaDomain;
import com.br.hotelmanagement.entity.records.in.ReservaIn;

public class ReservaInToReservaDomainAdapter {

    public static ReservaInToReservaDomainAdapter inicializa() {
        return new ReservaInToReservaDomainAdapter();
    }

    public ReservaDomain converte(ReservaIn reservaIn){
        ReservaDomain reservaDomain = new ReservaDomain();
        reservaDomain.setHospede(reservaIn.hospede());
        reservaDomain.setCheckIn(reservaIn.checkIn());
        reservaDomain.setCheckOut(reservaIn.checkOut());
        reservaDomain.setEstacionamento(reservaIn.estacionamento());

        return reservaDomain;
    }
}
