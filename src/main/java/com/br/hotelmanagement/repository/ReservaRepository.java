package com.br.hotelmanagement.repository;

import com.br.hotelmanagement.domain.ReservaDomain;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface ReservaRepository extends JpaRepository<ReservaDomain, Long> {

    @Query("SELECT r FROM ReservaDomain r WHERE r.checkIn IS NOT NULL AND " +
            "(r.checkOut IS NULL OR r.checkOut > CURRENT_TIMESTAMP) AND r.status = 'A'")
    Page<ReservaDomain> listarReservasEmAbertoComHospedes(Pageable pageable);

    @Query("SELECT SUM(r.valorTotal) FROM ReservaDomain r WHERE r.hospede.id = :hospedeId")
    BigDecimal somarValoresReservas(@Param("hospedeId") Long idHospede);

    @Query("SELECT r.valorTotal FROM ReservaDomain r WHERE r.hospede.id = :hospedeId AND " +
            "r.checkIn = (SELECT MAX(r2.checkIn) FROM ReservaDomain r2 WHERE r2.hospede.id = :hospedeId)")
    BigDecimal valorUltimaReserva(@Param("hospedeId") Long idHospede);

    @Query("SELECT r FROM ReservaDomain r " +
            "WHERE r.status = 'F' " +
            "AND r.checkOut IS NOT NULL " +
            "AND r.checkOut <= CURRENT_DATE")
    Page<ReservaDomain> listarReservasFinalizadasComHospedes(Pageable pageable);
}
