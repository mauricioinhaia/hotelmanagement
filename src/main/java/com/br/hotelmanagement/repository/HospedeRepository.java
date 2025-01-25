package com.br.hotelmanagement.repository;

import com.br.hotelmanagement.domain.HospedeDomain;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HospedeRepository extends JpaRepository<HospedeDomain, Long> {

    @Query("SELECT h FROM HospedeDomain h WHERE "
            + "(:nome      IS NULL OR h.nome      LIKE %:nome%)      AND "
            + "(:documento IS NULL OR h.documento LIKE %:documento%) AND "
            + "(:email     IS NULL OR h.email     LIKE %:email%)     AND "
            + "(:telefone  IS NULL OR h.telefone  LIKE %:telefone%)")
    Page<HospedeDomain> buscarHospedes(@Param("nome") String nome,
                                       @Param("documento") String documento,
                                       @Param("email") String email,
                                       @Param("telefone") String telefone,
                                       Pageable pageable);
}
