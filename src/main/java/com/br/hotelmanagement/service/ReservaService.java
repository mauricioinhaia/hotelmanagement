package com.br.hotelmanagement.service;

import com.br.hotelmanagement.dataaccess.query.ReservaDomainQueryDataAccess;
import com.br.hotelmanagement.domain.ReservaDomain;
import com.br.hotelmanagement.entity.adapter.reserva.ReservaDomainToReservaOutAdapter;
import com.br.hotelmanagement.entity.records.out.ReservaOut;
import com.br.hotelmanagement.exception.DataAccessException;
import com.br.hotelmanagement.exception.HospedeNotFoundException;
import com.br.hotelmanagement.response.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ReservaService {

    private final ReservaDomainQueryDataAccess reservaDomainQueryDataAccess;

    public ReservaService(ReservaDomainQueryDataAccess reservaDomainQueryDataAccess) {
        this.reservaDomainQueryDataAccess = reservaDomainQueryDataAccess;
    }

    public PageResponse<ReservaOut> listarHospedesNoHotel(Pageable pageable) {
        try {
            Page<ReservaDomain> paginaReserva = this.reservaDomainQueryDataAccess.listarHospedesNoHotel(pageable);
            Page<ReservaOut> paginaOut = paginaReserva.map(reserva -> ReservaDomainToReservaOutAdapter.inicializa().converte(reserva));
            return PageResponse.of(paginaOut);
        } catch (DataAccessException e) {
            throw new HospedeNotFoundException(e.getMessage(), e.getSource());
        }
    }
}
