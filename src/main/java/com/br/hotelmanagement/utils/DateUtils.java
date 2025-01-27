package com.br.hotelmanagement.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class DateUtils {

    public static long calcularDiasDeSemana(LocalDateTime dataInicial, LocalDateTime dataFinal) {
        return dataInicial.toLocalDate()
                .datesUntil(dataFinal.toLocalDate())
                .filter(data -> !isFimDeSemana(data))
                .count();
    }

    public static long calcularFinaisDeSemana(LocalDateTime dataInicial, LocalDateTime dataFinal) {
        return dataInicial.toLocalDate()
                .datesUntil(dataFinal.toLocalDate())
                .filter(DateUtils::isFimDeSemana)
                .count();
    }

    public static boolean isFimDeSemana(LocalDate data) {
        DayOfWeek dia = data.getDayOfWeek();
        return dia == DayOfWeek.SATURDAY || dia == DayOfWeek.SUNDAY;
    }
}
