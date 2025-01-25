package com.br.hotelmanagement.dataaccess.query;

import com.br.hotelmanagement.domain.ReservaDomain;
import com.br.hotelmanagement.exception.DataAccessException;
import com.br.hotelmanagement.repository.ReservaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ReservaDomainQueryDataAccess {

    private final ReservaRepository reservaRepository;

    public ReservaDomainQueryDataAccess(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    public Page<ReservaDomain> listarReservasEmAbertoComHospedes(Pageable pageable) {
        Page<ReservaDomain> reservasEmAberto = this.reservaRepository.listarReservasEmAbertoComHospedes(pageable);

        if (reservasEmAberto.isEmpty()) {
            throw new DataAccessException("Não ha hóspedes no hotel", "listarReservasEmAbertoComHospedes");
        }

        return reservasEmAberto;
    }

    public BigDecimal somarValoresReservas(Long idHospede) {
        BigDecimal somaValores = this.reservaRepository.somarValoresReservas(idHospede);
        return somaValores != null ? somaValores : BigDecimal.ZERO;
    }

    public BigDecimal valorUltimaReserva(Long idHospede) {
        BigDecimal valorUltima = this.reservaRepository.valorUltimaReserva(idHospede);
        return valorUltima != null ? valorUltima : BigDecimal.ZERO;
    }

    public Page<ReservaDomain> listarReservasFinalizadasComHospedes(Pageable pageable) {
        Page<ReservaDomain> reservasFinalizadas = this.reservaRepository.listarReservasFinalizadasComHospedes(pageable);

        if (reservasFinalizadas.isEmpty()) {
            throw new DataAccessException("Não foram encontrados hóspedes", "listarReservasFinalizadasComHospedes");
        }

        return reservasFinalizadas;
    }
}
