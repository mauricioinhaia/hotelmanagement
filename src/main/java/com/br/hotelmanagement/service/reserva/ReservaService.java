package com.br.hotelmanagement.service.reserva;

import com.br.hotelmanagement.dataaccess.command.ReservaDomainCommandDataAccess;
import com.br.hotelmanagement.dataaccess.query.ReservaDomainQueryDataAccess;
import com.br.hotelmanagement.domain.ReservaDomain;
import com.br.hotelmanagement.entity.adapter.reserva.ReservaDomainToReservaComValoresOutAdapter;
import com.br.hotelmanagement.entity.adapter.reserva.ReservaDomainToReservaOutAdapter;
import com.br.hotelmanagement.entity.adapter.reserva.ReservaInToReservaDomainAdapter;
import com.br.hotelmanagement.entity.records.in.ReservaIn;
import com.br.hotelmanagement.entity.records.out.ReservaComValoresOut;
import com.br.hotelmanagement.entity.records.out.ReservaOut;
import com.br.hotelmanagement.exception.DataAccessException;
import com.br.hotelmanagement.exception.HospedeNotFoundException;
import com.br.hotelmanagement.response.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;

@Service
public class ReservaService {

    private final ReservaDomainQueryDataAccess reservaDomainQueryDataAccess;
    private final ReservaDomainCommandDataAccess reservaDomainCommandDataAccess;
    private final ReservaCalculadoraService reservaCalculadoraService;
    private final ReservaStatusService reservaStatusService;

    public ReservaService(ReservaDomainQueryDataAccess reservaDomainQueryDataAccess, ReservaDomainCommandDataAccess reservaDomainCommandDataAccess, ReservaCalculadoraService reservaCalculadoraService, ReservaStatusService reservaStatusService) {
        this.reservaDomainQueryDataAccess = reservaDomainQueryDataAccess;
        this.reservaDomainCommandDataAccess = reservaDomainCommandDataAccess;
        this.reservaCalculadoraService = reservaCalculadoraService;
        this.reservaStatusService = reservaStatusService;
    }

    public ReservaOut criar(ReservaIn reservaIn) {
        ReservaDomain reservaDomain = ReservaInToReservaDomainAdapter.inicializa().converte(reservaIn);
        BigDecimal valorTotalReserva = Objects.isNull(reservaDomain.getCheckOut()) ? BigDecimal.ZERO :
                this.reservaCalculadoraService.calcularValorReserva(reservaDomain);
        reservaDomain.setValorTotal(valorTotalReserva);
        reservaDomain.setStatus(reservaStatusService.definirStatusReserva(reservaDomain).getSigla());
        ReservaDomain reservaDomainSalva = this.reservaDomainCommandDataAccess.save(reservaDomain);

        return ReservaDomainToReservaOutAdapter.inicializa().converte(reservaDomainSalva);
    }

    public PageResponse<ReservaComValoresOut> listarReservasEmAbertoComHospedes(Pageable pageable) {
        try {
            Page<ReservaDomain> paginaReserva = this.reservaDomainQueryDataAccess.listarReservasEmAbertoComHospedes(pageable);
            Page<ReservaComValoresOut> paginaOut = paginaReserva.map(reserva -> {
                ReservaComValoresOut reservaComValoresOut = ReservaDomainToReservaComValoresOutAdapter
                        .inicializa().converte(reserva);
                return reservaCalculadoraService.buscarValoresReservas(reservaComValoresOut, reserva.getHospede().getId());
            });
            return PageResponse.of(paginaOut);
        } catch (DataAccessException e) {
            throw new HospedeNotFoundException(e.getMessage(), e.getSource());
        }
    }

    public PageResponse<ReservaComValoresOut> listarReservasFinalizadasComHospedes(Pageable pageable) {
        try {
            Page<ReservaDomain> paginaReserva = this.reservaDomainQueryDataAccess.listarReservasFinalizadasComHospedes(pageable);
            Page<ReservaComValoresOut> paginaOut = paginaReserva.map(reserva -> {
                ReservaComValoresOut reservaComValoresOut = ReservaDomainToReservaComValoresOutAdapter
                        .inicializa().converte(reserva);
                return reservaCalculadoraService.buscarValoresReservas(reservaComValoresOut, reserva.getHospede().getId());
            });
            return PageResponse.of(paginaOut);
        } catch (DataAccessException e) {
            throw new HospedeNotFoundException(e.getMessage(), e.getSource());
        }
    }
}