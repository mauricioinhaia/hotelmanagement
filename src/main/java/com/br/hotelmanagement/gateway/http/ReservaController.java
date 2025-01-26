package com.br.hotelmanagement.gateway.http;

import com.br.hotelmanagement.entity.records.in.ReservaIn;
import com.br.hotelmanagement.entity.records.out.ReservaComValoresOut;
import com.br.hotelmanagement.entity.records.out.ReservaOut;
import com.br.hotelmanagement.response.PageResponse;
import com.br.hotelmanagement.service.reserva.ReservaService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservas")
public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReservaOut criar(@RequestBody ReservaIn reservaIn){
        return this.reservaService.criar(reservaIn);
    }

    @GetMapping("/reservas-em-aberto-com-hospedes")
    @ResponseStatus(HttpStatus.OK)
    public PageResponse<ReservaComValoresOut> listarReservasEmAbertoComHospedes(@PageableDefault(sort = "id", direction = Sort.Direction.ASC)
                                                                                Pageable pageable) {
        return this.reservaService.listarReservasEmAbertoComHospedes(pageable);
    }

    @GetMapping("/reservas-finalizadas-com-hospedes")
    @ResponseStatus(HttpStatus.OK)
    public PageResponse<ReservaComValoresOut> listarReservasFinalizadasComHospedes(@PageableDefault(sort = "id", direction = Sort.Direction.ASC)
                                                                                   Pageable pageable) {
        return this.reservaService.listarReservasFinalizadasComHospedes(pageable);
    }
}
