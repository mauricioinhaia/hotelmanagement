package com.br.hotelmanagement.service.hospede;

import com.br.hotelmanagement.dataaccess.command.HospedeDomainCommandDataAccess;
import com.br.hotelmanagement.dataaccess.query.HospedeDomainQueryDataAccess;
import com.br.hotelmanagement.domain.HospedeDomain;
import com.br.hotelmanagement.entity.records.out.HospedeOut;
import com.br.hotelmanagement.response.PageResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
public class HospedeServiceTest {

    @Mock
    private HospedeDomainQueryDataAccess hospedeDomainQueryDataAccess;
    @Mock
    private HospedeDomainCommandDataAccess hospedeDomainCommandDataAccess;

    @InjectMocks
    private HospedeService hospedeService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testListarTodos() {
        Pageable pageable = PageRequest.of(0, 10);
        HospedeDomain hospedeDomain = new HospedeDomain();
        hospedeDomain.setId(1L);
        hospedeDomain.setNome("João");
        hospedeDomain.setDocumento("12345678911");
        hospedeDomain.setTelefone("(49) 99988-7766");
        hospedeDomain.setEmail("teste@teste.com");
        Page<HospedeDomain> paginaHospedes = new PageImpl<>(List.of(hospedeDomain));

        when(this.hospedeDomainQueryDataAccess.listarTodos(pageable)).thenReturn(paginaHospedes);

        PageResponse<HospedeOut> resultado = this.hospedeService.listarTodos(pageable);

        assertNotNull(resultado);
        assertEquals(1, resultado.content().size());
        assertEquals(1L, resultado.content().getFirst().id());
        assertEquals("12345678911", resultado.content().getFirst().Documento());
        assertEquals("João", resultado.content().getFirst().nome());
        assertEquals("(49) 99988-7766", resultado.content().getFirst().telefone());
        assertEquals("teste@teste.com", resultado.content().getFirst().email());
    }


//    @Test
//    public void testCriar() {
//        HospedeIn hospedeIn = new HospedeIn(
//                "João",
//                "12345678911",
//                "(49) 99988-7766",
//                "teste@teste.com"
//        );
//
//        HospedeDomain hospedeDomain = new HospedeDomain();
//        hospedeDomain.setId(1L);
//        hospedeDomain.setNome("João");
//        hospedeDomain.setDocumento("12345678911");
//        hospedeDomain.setTelefone("(49) 99988-7766");
//        hospedeDomain.setEmail("teste@teste.com");
//
//        when(hospedeDomainCommandDataAccess.save(any(HospedeDomain.class))).thenReturn(hospedeDomain);
//
//
//    }
}
