package com.br.hotelmanagement.gateway.http;

import com.br.hotelmanagement.entity.records.in.ReservaIn;
import com.br.hotelmanagement.entity.records.out.ReservaComValoresOut;
import com.br.hotelmanagement.entity.records.out.ReservaOut;
import com.br.hotelmanagement.response.PageResponse;
import com.br.hotelmanagement.service.reserva.ReservaService;
import com.br.hotelmanagement.validator.ReservaValidator;
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

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ReservaOut buscarPorId(@PathVariable Long id) {
        return this.reservaService.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReservaOut criar(@RequestBody ReservaIn reservaIn) {
        ReservaValidator.inicializa().validarReservaIn(reservaIn);
        return this.reservaService.criar(reservaIn);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        this.reservaService.deletar(id);
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
