package com.br.hotelmanagement.dataaccess.query;

import com.br.hotelmanagement.domain.TarifaDomain;
import com.br.hotelmanagement.repository.TarifaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TarifaDomainQueryDataAccess {

    private final TarifaRepository tarifaRepository;

    public TarifaDomainQueryDataAccess(TarifaRepository tarifaRepository) {
        this.tarifaRepository = tarifaRepository;
    }

    public List<TarifaDomain> tarifas(){
        return this.tarifaRepository.findAll();
    }
}
