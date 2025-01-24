package com.br.hotelmanagement.entity.adapter.hospede;

import com.br.hotelmanagement.domain.HospedeDomain;
import com.br.hotelmanagement.entity.records.out.HospedeOut;

public class HospedeDomainToHospedeOutAdapter {

    public static HospedeDomainToHospedeOutAdapter inicializa() {
        return new HospedeDomainToHospedeOutAdapter();
    }

    public HospedeOut converte(HospedeDomain hospedeDomain) {
        return new HospedeOut(
                hospedeDomain.getId(),
                hospedeDomain.getNome(),
                hospedeDomain.getDocumento(),
                hospedeDomain.getEmail(),
                hospedeDomain.getTelefone()
        );
    }
}
