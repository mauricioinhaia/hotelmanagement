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

    @Query(value = "SELECT r.valortotal FROM reservas r WHERE r.hospede = :hospedeId AND " +
            "r.checkin = (SELECT r2.checkin FROM reservas r2 WHERE r2.hospede = :hospedeId " +
            "ORDER BY r2.checkin DESC LIMIT 1) " +
            "ORDER BY r.id DESC",
            nativeQuery = true)
    BigDecimal valorUltimaReserva(@Param("hospedeId") Long idHospede);

    @Query("SELECT r FROM ReservaDomain r " +
            "WHERE r.status = 'F' " +
            "AND r.checkOut IS NOT NULL " +
            "AND r.checkOut <= CURRENT_DATE")
    Page<ReservaDomain> listarReservasFinalizadasComHospedes(Pageable pageable);
}
