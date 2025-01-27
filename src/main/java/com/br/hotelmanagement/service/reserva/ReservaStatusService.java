package com.br.hotelmanagement.service.reserva;

import com.br.hotelmanagement.domain.ReservaDomain;
import com.br.hotelmanagement.shared.enums.ReservaStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class ReservaStatusService {
    public ReservaStatus definirStatusReserva(ReservaDomain reservaDomain) {
        if (Objects.nonNull(reservaDomain.getCheckIn()) && Objects.nonNull(reservaDomain.getCheckOut())) {
            return reservaDomain.getCheckOut().isBefore(LocalDateTime.now())
                    ? ReservaStatus.FINALIZADO
                    : ReservaStatus.ABERTO;
        }

        if (Objects.nonNull(reservaDomain.getCheckIn()) && Objects.isNull(reservaDomain.getCheckOut())) {
            return ReservaStatus.ABERTO;
        }

        return ReservaStatus.ABERTO;
    }
}
