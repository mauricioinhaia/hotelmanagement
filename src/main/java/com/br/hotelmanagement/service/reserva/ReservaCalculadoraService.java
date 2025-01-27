package com.br.hotelmanagement.service.reserva;

import com.br.hotelmanagement.dataaccess.query.ReservaDomainQueryDataAccess;
import com.br.hotelmanagement.dataaccess.query.TarifaDomainQueryDataAccess;
import com.br.hotelmanagement.domain.ReservaDomain;
import com.br.hotelmanagement.domain.TarifaDomain;
import com.br.hotelmanagement.entity.records.out.ReservaComValoresOut;
import com.br.hotelmanagement.utils.DateUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@Service
public class ReservaCalculadoraService {

    private final ReservaDomainQueryDataAccess reservaDomainQueryDataAccess;
    private final TarifaDomainQueryDataAccess tarifaDomainQueryDataAccess;

    public ReservaCalculadoraService(ReservaDomainQueryDataAccess reservaDomainQueryDataAccess,
                                     TarifaDomainQueryDataAccess tarifaDomainQueryDataAccess) {
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
        long diasDeSemana = DateUtils.calcularDiasDeSemana(reservaDomain.getCheckIn(), reservaDomain.getCheckOut());
        long diasFinaisDeSemana = DateUtils.calcularFinaisDeSemana(reservaDomain.getCheckIn(), reservaDomain.getCheckOut());
        //TODO: fazer enum para tipservico
        BigDecimal valorTotalReserva = this.calcularValorPorTipo(tarifas, "H", diasDeSemana, diasFinaisDeSemana);

        BigDecimal valorEstacionamento = BigDecimal.ZERO;
        if (reservaDomain.isEstacionamento()) {
            valorEstacionamento = this.calcularValorPorTipo(tarifas, "E", diasDeSemana, diasFinaisDeSemana);
        }

        valorTotalReserva = valorTotalReserva.add(this.calcularDiariaExtra(
                tarifas, reservaDomain.getCheckOut(), "H"));
        if (reservaDomain.isEstacionamento()) {
            valorEstacionamento = valorEstacionamento.add(this.calcularDiariaExtra(
                    tarifas, reservaDomain.getCheckOut(), "E"));
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

    private BigDecimal calcularDiariaExtra(List<TarifaDomain> tarifas, LocalDateTime checkout, String tipoServico) {
        if (Objects.isNull(tarifas) || tarifas.isEmpty() || Objects.isNull(checkout) || Objects.isNull(tipoServico)) {
            throw new IllegalArgumentException("Para calcular a Diaria Extra precisa informar: as tarifas," +
                    " checkout e tipo serviço");
        }

        if (checkout.toLocalTime().isAfter(LocalTime.of(16, 30))) {
            boolean isFinalDeSemana = DateUtils.isFimDeSemana(checkout.toLocalDate());
            return tarifas.stream()
                    .filter(tarifa -> tarifa.getTipoServico().equals(tipoServico) && tarifa.isFimdesemana() == isFinalDeSemana)
                    .map(TarifaDomain::getValor)
                    .findFirst()
                    .orElse(BigDecimal.ZERO);
        }
        return BigDecimal.ZERO;
    }
}
