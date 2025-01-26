package com.br.hotelmanagement.dataaccess.query;

import com.br.hotelmanagement.domain.ReservaDomain;
import com.br.hotelmanagement.exception.DataAccessException;
import com.br.hotelmanagement.repository.ReservaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

@Component
public class ReservaDomainQueryDataAccess {

    private final ReservaRepository reservaRepository;

    public ReservaDomainQueryDataAccess(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    public ReservaDomain findById(Long id) {
        Optional<ReservaDomain> reservaDomainOptional = this.reservaRepository.findById(id);
        return this.getReservaOptional(reservaDomainOptional, "findById");
    }

    private ReservaDomain getReservaOptional(Optional<ReservaDomain> reservaDomainOptional, String execucao) {
        if (reservaDomainOptional.isPresent()) {
            return reservaDomainOptional.get();
        } else {
            throw new DataAccessException("Reserva não encontrada", execucao);
        }
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
