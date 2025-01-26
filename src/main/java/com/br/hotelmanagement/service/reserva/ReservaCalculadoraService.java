package com.br.hotelmanagement.service.reserva;

import com.br.hotelmanagement.dataaccess.query.ReservaDomainQueryDataAccess;
import com.br.hotelmanagement.dataaccess.query.TarifaDomainQueryDataAccess;
import com.br.hotelmanagement.domain.ReservaDomain;
import com.br.hotelmanagement.domain.TarifaDomain;
import com.br.hotelmanagement.entity.records.out.ReservaComValoresOut;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservaCalculadoraService {

    private final ReservaDomainQueryDataAccess reservaDomainQueryDataAccess;
    private final TarifaDomainQueryDataAccess tarifaDomainQueryDataAccess;

    public ReservaCalculadoraService(ReservaDomainQueryDataAccess reservaDomainQueryDataAccess, TarifaDomainQueryDataAccess tarifaDomainQueryDataAccess) {
        this.reservaDomainQueryDataAccess = reservaDomainQueryDataAccess;
        this.tarifaDomainQueryDataAccess = tarifaDomainQueryDataAccess;
    }

    public ReservaComValoresOut buscarValoresReservas(ReservaComValoresOut reservaComValoresOut, Long idHospede) {
        BigDecimal valorTotalReservas = this.reservaDomainQueryDataAccess.somarValoresReservas(idHospede);
        BigDecimal valorUltimaReserva = this.reservaDomainQueryDataAccess.valorUltimaReserva(idHospede);

        return reservaComValoresOut.withValorReservas(valorTotalReservas, valorUltimaReserva);
    }

    public BigDecimal calcularValorReserva(ReservaDomain reservaDomain) {
        List<TarifaDomain> tarifas = this.tarifaDomainQueryDataAccess.tarifas();
        long diasDeSemana = this.calcularDiasDeSemana(reservaDomain.getCheckIn(), reservaDomain.getCheckOut());
        long diasFinaisDeSemana = this.calcularFinaisDeSemana(reservaDomain.getCheckIn(), reservaDomain.getCheckOut());

        BigDecimal valorTotalReserva = calcularValorPorTipo(tarifas, "H", diasDeSemana, diasFinaisDeSemana);

        BigDecimal valorEstacionamento = BigDecimal.ZERO;
        if (reservaDomain.isEstacionamento()) {
            valorEstacionamento = calcularValorPorTipo(tarifas, "E", diasDeSemana, diasFinaisDeSemana);
        }

        return valorTotalReserva.add(valorEstacionamento);
    }

    private BigDecimal calcularValorPorTipo(List<TarifaDomain> tarifas,
                                            String tipoServico,
                                            long diasDeSemana,
                                            long diasFinaisDeSemana) {
        BigDecimal valorDiaDeSemana = tarifas.stream()
                .filter(tarifa -> tarifa.getTipoServico().equals(tipoServico) && !tarifa.isFimdesemana())
                .map(TarifaDomain::getValor)
                .findFirst()
                .orElse(BigDecimal.ZERO);

        BigDecimal valorFinalDeSemana = tarifas.stream()
                .filter(tarifa -> tarifa.getTipoServico().equals(tipoServico) && tarifa.isFimdesemana())
                .map(TarifaDomain::getValor)
                .findFirst()
                .orElse(BigDecimal.ZERO);

        return valorDiaDeSemana.multiply(BigDecimal.valueOf(diasDeSemana))
                .add(valorFinalDeSemana.multiply(BigDecimal.valueOf(diasFinaisDeSemana)));
    }

    private long calcularDiasDeSemana(LocalDateTime checkIn, LocalDateTime checkOut) {
        return checkIn.toLocalDate()
                .datesUntil(checkOut.toLocalDate())
                .filter(data -> !isFimDeSemana(data))
                .count();
    }

    private long calcularFinaisDeSemana(LocalDateTime checkIn, LocalDateTime checkOut) {
        return checkIn.toLocalDate()
                .datesUntil(checkOut.toLocalDate())
                .filter(this::isFimDeSemana)
                .count();
    }

    private boolean isFimDeSemana(LocalDate data) {
        DayOfWeek dia = data.getDayOfWeek();
        return dia == DayOfWeek.SATURDAY || dia == DayOfWeek.SUNDAY;
    }

}
