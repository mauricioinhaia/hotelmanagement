package com.br.hotelmanagement.entity.adapter.hospede;

import com.br.hotelmanagement.domain.HospedeDomain;
import com.br.hotelmanagement.entity.records.in.HospedeIn;

public class HospedeInToHospedeDomainAdapter {

    public static HospedeInToHospedeDomainAdapter inicializa() {
        return new HospedeInToHospedeDomainAdapter();
    }

    public HospedeDomain converte(HospedeIn entrada) {
        HospedeDomain hospedeDomain = new HospedeDomain();
        hospedeDomain.setNome(entrada.nome());
        hospedeDomain.setDocumento(entrada.documento());
        hospedeDomain.setEmail(entrada.email());
        hospedeDomain.setTelefone(entrada.telefone());

        return hospedeDomain;
    }
}
