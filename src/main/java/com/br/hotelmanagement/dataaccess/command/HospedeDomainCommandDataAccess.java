package com.br.hotelmanagement.dataaccess.command;

import com.br.hotelmanagement.domain.HospedeDomain;
import com.br.hotelmanagement.repository.HospedeRepository;
import org.springframework.stereotype.Component;

@Component
public class HospedeDomainCommandDataAccess {

    private final HospedeRepository hospedeRepository;

    public HospedeDomainCommandDataAccess(HospedeRepository hospedeRepository) {
        this.hospedeRepository = hospedeRepository;
    }

    public HospedeDomain save(HospedeDomain hospedeDomain) {
        return this.hospedeRepository.save(hospedeDomain);
    }

    public void deleteById(Long id) {
        this.hospedeRepository.deleteById(id);
    }
}
