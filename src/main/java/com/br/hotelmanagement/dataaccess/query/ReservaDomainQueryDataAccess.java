package com.br.hotelmanagement.dataaccess.query;

import com.br.hotelmanagement.domain.ReservaDomain;
import com.br.hotelmanagement.exception.DataAccessException;
import com.br.hotelmanagement.repository.ReservaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class ReservaDomainQueryDataAccess {

    private final ReservaRepository reservaRepository;

    public ReservaDomainQueryDataAccess(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    public Page<ReservaDomain> listarHospedesNoHotel(Pageable pageable) {
        Page<ReservaDomain> hospedesNoHotel = this.reservaRepository.listarHospedesNoHotel(pageable);

        if (hospedesNoHotel.isEmpty()) {
            throw new DataAccessException("Nao ha hóspedes no hotel", "listarHospedesNoHotel");
        }

        return hospedesNoHotel;
    }
}
