package com.br.hotelmanagement.dataaccess.command;

import com.br.hotelmanagement.domain.ReservaDomain;
import com.br.hotelmanagement.repository.ReservaRepository;
import org.springframework.stereotype.Component;

@Component
public class ReservaDomainCommandDataAccess {

    private final ReservaRepository reservaRepository;

    public ReservaDomainCommandDataAccess(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    public ReservaDomain save(ReservaDomain reservaDomain) {
        return this.reservaRepository.save(reservaDomain);
    }
}
