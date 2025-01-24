package com.br.hotelmanagement.gateway.http;

import com.br.hotelmanagement.entity.records.in.HospedeIn;
import com.br.hotelmanagement.entity.records.out.HospedeOut;
import com.br.hotelmanagement.response.PageResponse;
import com.br.hotelmanagement.service.HospedeService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hospedes")
public class HospedeController {

    private final HospedeService hospedeService;

    public HospedeController(HospedeService hospedeService) {
        this.hospedeService = hospedeService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PageResponse<HospedeOut> listarTodos(@PageableDefault(sort = "id", direction = Sort.Direction.ASC)
                                            Pageable pageable) {
        return this.hospedeService.listarTodos(pageable);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public HospedeOut buscarPorId(@PathVariable Long id){
        return this.hospedeService.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HospedeOut criar(@RequestBody HospedeIn hospede) {
        return this.hospedeService.criar(hospede);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar (@PathVariable Long id) {
        this.hospedeService.deletar(id);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public HospedeOut atualizar (@PathVariable Long id, @RequestBody HospedeIn hospedeIn) {
        return this.hospedeService.atualizar(id, hospedeIn);
    }
}
