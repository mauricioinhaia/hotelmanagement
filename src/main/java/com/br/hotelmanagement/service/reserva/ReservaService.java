package com.br.hotelmanagement.service.reserva;

import com.br.hotelmanagement.dataaccess.command.ReservaDomainCommandDataAccess;
import com.br.hotelmanagement.dataaccess.query.ReservaDomainQueryDataAccess;
import com.br.hotelmanagement.domain.ReservaDomain;
import com.br.hotelmanagement.shared.enums.ReservaStatus;
import com.br.hotelmanagement.entity.adapter.reserva.ReservaDomainToReservaComValoresOutAdapter;
import com.br.hotelmanagement.entity.adapter.reserva.ReservaDomainToReservaOutAdapter;
import com.br.hotelmanagement.entity.adapter.reserva.ReservaInToReservaDomainAdapter;
import com.br.hotelmanagement.entity.records.in.ReservaIn;
import com.br.hotelmanagement.entity.records.out.ReservaComValoresOut;
import com.br.hotelmanagement.entity.records.out.ReservaOut;
import com.br.hotelmanagement.exception.DataAccessException;
import com.br.hotelmanagement.exception.ReservaNaoPodeSerExcluidaException;
import com.br.hotelmanagement.exception.ReservaNotFoundException;
import com.br.hotelmanagement.response.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class ReservaService {

    private final ReservaDomainQueryDataAccess reservaDomainQueryDataAccess;
    private final ReservaDomainCommandDataAccess reservaDomainCommandDataAccess;
    private final ReservaCalculadoraService reservaCalculadoraService;
    private final ReservaStatusService reservaStatusService;

    public ReservaService(ReservaDomainQueryDataAccess reservaDomainQueryDataAccess,
                          ReservaDomainCommandDataAccess reservaDomainCommandDataAccess,
                          ReservaCalculadoraService reservaCalculadoraService,
                          ReservaStatusService reservaStatusService) {
        this.reservaDomainQueryDataAccess = reservaDomainQueryDataAccess;
        this.reservaDomainCommandDataAccess = reservaDomainCommandDataAccess;
        this.reservaCalculadoraService = reservaCalculadoraService;
        this.reservaStatusService = reservaStatusService;
    }

    public ReservaOut buscarPorId(Long id) {
        try {
            return ReservaDomainToReservaOutAdapter.inicializa().converte(this.reservaDomainQueryDataAccess.findById(id));
        } catch (DataAccessException e) {
            throw new ReservaNotFoundException(e.getMessage(), e.getSource());
        }
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

    public void deletar(Long id) {
        try {
            ReservaDomain reservaDomain = this.reservaDomainQueryDataAccess.findById(id);
            if (this.reservaNaoPodeSerExcluida(reservaDomain.getCheckOut(), reservaDomain.getStatus())) {
                throw new ReservaNaoPodeSerExcluidaException("A reserva não pode ser excluída", getClass().getSimpleName());
            }

            this.reservaDomainCommandDataAccess.deleteById(id);
        } catch (DataAccessException e) {
            throw new ReservaNotFoundException(e.getMessage(), e.getSource());
        }
    }

    private boolean reservaNaoPodeSerExcluida(LocalDateTime checkOut, String status) {
        return Objects.nonNull(checkOut) &&
                ReservaStatus.FINALIZADO.getSigla().equals(status);
    }

    public ReservaOut atualizar(Long id, ReservaIn reservaIn) {
        try {
            ReservaDomain reservaAtualizada = this.atualizaCampos(
                    this.reservaDomainQueryDataAccess.findById(id), reservaIn);
            ReservaDomain reservaSalva = this.reservaDomainCommandDataAccess.save(reservaAtualizada);
            return ReservaDomainToReservaOutAdapter.inicializa().converte(reservaSalva);
        } catch (DataAccessException e) {
            throw new ReservaNotFoundException(e.getMessage(), e.getSource());
        }
    }

    private ReservaDomain atualizaCampos(ReservaDomain reservaDomain, ReservaIn reservaIn) {
        if (Objects.nonNull(reservaIn.hospede())) {
            reservaDomain.setHospede(reservaIn.hospede());
        }
        if (Objects.nonNull(reservaIn.checkOut())) {
            reservaDomain.setCheckOut(reservaIn.checkOut());
        }
        if (Objects.nonNull(reservaIn.checkIn())) {
            reservaDomain.setCheckIn(reservaIn.checkIn());
        }
        if (Objects.nonNull(reservaIn.valorTotal())) {
            reservaDomain.setValorTotal(reservaIn.valorTotal());
        }
        reservaDomain.setEstacionamento(reservaIn.estacionamento());

        return reservaDomain;
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
            throw new ReservaNotFoundException(e.getMessage(), e.getSource());
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
            throw new ReservaNotFoundException(e.getMessage(), e.getSource());
        }
    }
}