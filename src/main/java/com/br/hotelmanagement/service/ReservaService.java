package com.br.hotelmanagement.service;

import com.br.hotelmanagement.dataaccess.query.ReservaDomainQueryDataAccess;
import com.br.hotelmanagement.domain.ReservaDomain;
import com.br.hotelmanagement.entity.adapter.reserva.ReservaDomainToReservaComValoresOutAdapter;
import com.br.hotelmanagement.entity.records.out.ReservaComValoresOut;
import com.br.hotelmanagement.exception.DataAccessException;
import com.br.hotelmanagement.exception.HospedeNotFoundException;
import com.br.hotelmanagement.response.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ReservaService {

    private final ReservaDomainQueryDataAccess reservaDomainQueryDataAccess;
    private final ReservaCalculadoraService reservaCalculadoraService;

    public ReservaService(ReservaDomainQueryDataAccess reservaDomainQueryDataAccess, ReservaCalculadoraService reservaCalculadoraService) {
        this.reservaDomainQueryDataAccess = reservaDomainQueryDataAccess;
        this.reservaCalculadoraService = reservaCalculadoraService;
    }

    public PageResponse<ReservaComValoresOut> listarHospedesNoHotel(Pageable pageable) {
        try {
            Page<ReservaDomain> paginaReserva = this.reservaDomainQueryDataAccess.listarHospedesNoHotel(pageable);
            Page<ReservaComValoresOut> paginaOut = paginaReserva.map(reserva -> {
                ReservaComValoresOut reservaComValoresOut = ReservaDomainToReservaComValoresOutAdapter
                        .inicializa().converte(reserva);
                return reservaCalculadoraService.calcularValoresReservas(reservaComValoresOut, reserva.getHospede().getId());
            });
            return PageResponse.of(paginaOut);
        } catch (DataAccessException e) {
            throw new HospedeNotFoundException(e.getMessage(), e.getSource());
        }
    }
}