package com.br.hotelmanagement.repository;

import com.br.hotelmanagement.domain.HospedeDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HospedeRepository extends JpaRepository<HospedeDomain, Long> {
}
