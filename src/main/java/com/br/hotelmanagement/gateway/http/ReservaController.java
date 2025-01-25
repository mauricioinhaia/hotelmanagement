package com.br.hotelmanagement.gateway.http;

import com.br.hotelmanagement.entity.records.out.ReservaComValoresOut;
import com.br.hotelmanagement.response.PageResponse;
import com.br.hotelmanagement.service.reserva.ReservaService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservas")
public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @GetMapping("/reservas-em-aberto-com-hospedes")
    @ResponseStatus(HttpStatus.OK)
    public PageResponse<ReservaComValoresOut> listarReservasEmAbertoComHospedes(@PageableDefault(sort = "id", direction = Sort.Direction.ASC)
                                                                                Pageable pageable) {
        return this.reservaService.listarReservasEmAbertoComHospedes(pageable);
    }
}
