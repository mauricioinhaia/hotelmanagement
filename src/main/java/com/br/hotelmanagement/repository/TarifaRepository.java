package com.br.hotelmanagement.repository;

import com.br.hotelmanagement.domain.TarifaDomain;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TarifaRepository extends JpaRepository<TarifaDomain, Long> {
}
