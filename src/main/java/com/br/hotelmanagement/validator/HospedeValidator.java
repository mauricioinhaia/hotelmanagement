package com.br.hotelmanagement.validator;

import com.br.hotelmanagement.entity.records.in.HospedeIn;
import com.br.hotelmanagement.exception.PayloadException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

public class HospedeValidator {

    public static HospedeValidator inicializa() {
        return new HospedeValidator();
    }

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    public void validarHospedeIn(HospedeIn hospedeIn) {
        Map<String, Object> erros = new HashMap<>();

        if (Objects.isNull(hospedeIn.nome())) {
            erros.put("nome", "O nome do hóspede não pode ser nulo");
        }

        if (Objects.isNull(hospedeIn.documento())) {
            erros.put("documento", "O documento do hóspede não pode ser nulo");
        } else if (hospedeIn.documento().length() < 11 || hospedeIn.documento().length() > 14) {
            erros.put("documento", "O documento deve conter entre 11 e 14 digitos");
        }

        if (Objects.nonNull(hospedeIn.telefone()) && hospedeIn.telefone().length() > 20) {
            erros.put("telefone", "O telefone não pode exceder 20 digitos");
        }

        if (Objects.nonNull(hospedeIn.email()) && !EMAIL_PATTERN.matcher(hospedeIn.email()).matches()) {
            erros.put("email", "O e-mail fornecido é inválido");
        }

        if (!erros.isEmpty()) {
            throw new PayloadException("Campos incorretos", this.getClass().getSimpleName(), erros);
        }
    }

    public void validarFiltrosBuscarHospedes(String nome,
                                             String documento,
                                             String email,
                                             String telefone) {
        Map<String, Object> erro = new HashMap<>();
        if (Objects.isNull(nome) && Objects.isNull(documento) && Objects.isNull(email) && Objects.isNull(telefone)) {
            erro.put("filtros", "Para buscar hóspedes favor informar: nome, documento, email ou telefone");
        }

        if(!erro.isEmpty()) {
            throw new PayloadException("Filtros necessários", this.getClass().getSimpleName(), erro);
        }
    }
}
