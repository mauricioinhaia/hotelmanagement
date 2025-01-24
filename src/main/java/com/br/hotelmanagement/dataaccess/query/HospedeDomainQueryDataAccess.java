package com.br.hotelmanagement.dataaccess.query;

import com.br.hotelmanagement.domain.HospedeDomain;
import com.br.hotelmanagement.exception.DataAccessException;
import com.br.hotelmanagement.repository.HospedeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class HospedeDomainQueryDataAccess {
    private final HospedeRepository hospedeRepository;

    public HospedeDomainQueryDataAccess(HospedeRepository hospedeRepository) {
        this.hospedeRepository = hospedeRepository;
    }

    public HospedeDomain findById(Long id) {
        Optional<HospedeDomain> hospedeOptional = this.hospedeRepository.findById(id);
        return this.getHospedeOptional(hospedeOptional, "findById");
    }

    private HospedeDomain getHospedeOptional(Optional<HospedeDomain> hospedeOptional, String execucao) {
        if (hospedeOptional.isPresent()) {
            return hospedeOptional.get();
        } else {
            throw new DataAccessException("Hospede não encontrado.", execucao);
        }
    }

    public Page<HospedeDomain> listarTodos(Pageable pageable) {
        return this.hospedeRepository.findAll(pageable);
    }
}
