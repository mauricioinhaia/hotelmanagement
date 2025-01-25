package com.br.hotelmanagement.repository;

import com.br.hotelmanagement.domain.ReservaDomain;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReservaRepository extends JpaRepository<ReservaDomain, Long> {

    @Query("SELECT r FROM ReservaDomain r WHERE r.checkIn IS NOT NULL AND " +
            "(r.checkOut IS NULL OR r.checkOut > CURRENT_TIMESTAMP) AND r.status = 'A'")
    Page<ReservaDomain> listarHospedesNoHotel(Pageable pageable);
}
