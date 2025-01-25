package com.br.hotelmanagement.service;

import com.br.hotelmanagement.dataaccess.command.HospedeDomainCommandDataAccess;
import com.br.hotelmanagement.dataaccess.query.HospedeDomainQueryDataAccess;
import com.br.hotelmanagement.domain.HospedeDomain;
import com.br.hotelmanagement.entity.adapter.hospede.HospedeDomainToHospedeOutAdapter;
import com.br.hotelmanagement.entity.adapter.hospede.HospedeInToHospedeDomainAdapter;
import com.br.hotelmanagement.entity.records.in.HospedeIn;
import com.br.hotelmanagement.entity.records.out.HospedeOut;
import com.br.hotelmanagement.exception.DataAccessException;
import com.br.hotelmanagement.exception.HospedeNotFoundException;
import com.br.hotelmanagement.response.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class HospedeService {

    private final HospedeDomainCommandDataAccess hospedeDomainCommandDataAccess;

    private final HospedeDomainQueryDataAccess hospedeDomainQueryDataAccess;

    public HospedeService(HospedeDomainCommandDataAccess hospedeDomainCommandDataAccess, HospedeDomainQueryDataAccess hospedeDomainQueryDataAccess) {
        this.hospedeDomainCommandDataAccess = hospedeDomainCommandDataAccess;
        this.hospedeDomainQueryDataAccess = hospedeDomainQueryDataAccess;
    }

    public PageResponse<HospedeOut> listarTodos(Pageable pageable) {
        Page<HospedeDomain> paginaHospedes = this.hospedeDomainQueryDataAccess.listarTodos(pageable);
        Page<HospedeOut> paginaOut = paginaHospedes.map(hospede -> HospedeDomainToHospedeOutAdapter.inicializa().converte(hospede));
        return PageResponse.of(paginaOut);
    }

    public HospedeOut buscarPorId(Long id) {
        try {
            return HospedeDomainToHospedeOutAdapter.inicializa().converte(this.hospedeDomainQueryDataAccess.findById(id));
        } catch (DataAccessException e) {
            throw new HospedeNotFoundException(e.getMessage(), e.getSource());
        }
    }

    public PageResponse<HospedeOut> buscarHospedes(String nome,
                                                   String documento,
                                                   String email,
                                                   String telefone,
                                                   Pageable pageable) {
        try {
            Page<HospedeDomain> paginaHospedes = this.hospedeDomainQueryDataAccess.buscarHospedes(
                    nome,
                    documento,
                    email,
                    telefone,
                    pageable);
            Page<HospedeOut> paginaOut = paginaHospedes.map(hospede -> HospedeDomainToHospedeOutAdapter.inicializa().converte(hospede));
            return PageResponse.of(paginaOut);
        } catch (DataAccessException e) {
            throw new HospedeNotFoundException(e.getMessage(), e.getSource());
        }
    }

    public HospedeOut criar(HospedeIn hospedeIn) {
        HospedeDomain hospede = this.hospedeDomainCommandDataAccess.save(HospedeInToHospedeDomainAdapter
                .inicializa().converte(hospedeIn));
        return HospedeDomainToHospedeOutAdapter.inicializa().converte(hospede);
    }

    public void deletar(Long id) {
        try {
            this.hospedeDomainQueryDataAccess.findById(id);
            this.hospedeDomainCommandDataAccess.deletar(id);
        } catch (DataAccessException e) {
            throw new HospedeNotFoundException(e.getMessage(), e.getSource());
        }
    }

    public HospedeOut atualizar(Long id, HospedeIn hospedeIn) {
        try {
            HospedeDomain hospedeAtualizado = this.atualizaCampos(this.hospedeDomainQueryDataAccess.findById(id), hospedeIn);
            HospedeDomain hospedeSalvo = this.hospedeDomainCommandDataAccess.save(hospedeAtualizado);
            return HospedeDomainToHospedeOutAdapter.inicializa().converte(hospedeSalvo);
        } catch (DataAccessException e) {
            throw new HospedeNotFoundException(e.getMessage(), e.getSource());
        }
    }

    private HospedeDomain atualizaCampos(HospedeDomain hospedeDomain, HospedeIn hospedeIn) {
        if (Objects.nonNull(hospedeIn.nome())) {
            hospedeDomain.setNome(hospedeIn.nome());
        }
        if (Objects.nonNull(hospedeIn.email())) {
            hospedeDomain.setEmail(hospedeIn.email());
        }
        if (Objects.nonNull(hospedeIn.telefone())) {
            hospedeDomain.setTelefone(hospedeIn.telefone());
        }

        return hospedeDomain;
    }
}
