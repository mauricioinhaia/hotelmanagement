package com.br.hotelmanagement.service.hospede;

import com.br.hotelmanagement.dataaccess.command.HospedeDomainCommandDataAccess;
import com.br.hotelmanagement.dataaccess.query.HospedeDomainQueryDataAccess;
import com.br.hotelmanagement.domain.HospedeDomain;
import com.br.hotelmanagement.entity.adapter.hospede.HospedeInToHospedeDomainAdapter;
import com.br.hotelmanagement.entity.records.in.HospedeIn;
import com.br.hotelmanagement.entity.records.out.HospedeOut;
import com.br.hotelmanagement.response.PageResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HospedeServiceTest {

    @Mock
    private HospedeDomainQueryDataAccess hospedeDomainQueryDataAccess;
    @Mock
    private HospedeDomainCommandDataAccess hospedeDomainCommandDataAccess;

    @InjectMocks
    private HospedeService hospedeService;

    @Test
    @DisplayName("QUANDO receber parametros de paginacao, DEVE gerar pagina com registros correspondentes")
    public void listarTodos_quandoReceberPaginacao_deveRetornarUmaPagina() {
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

        verify(this.hospedeDomainQueryDataAccess).listarTodos(any(Pageable.class));
        assertNotNull(resultado);
        assertEquals(1, resultado.content().size());
        assertEquals(1L, resultado.content().getFirst().id());
        assertEquals("12345678911", resultado.content().getFirst().Documento());
        assertEquals("João", resultado.content().getFirst().nome());
        assertEquals("(49) 99988-7766", resultado.content().getFirst().telefone());
        assertEquals("teste@teste.com", resultado.content().getFirst().email());
    }


    @Test
    @DisplayName("QUANDO receber dados válidos de HospedeIn, DEVE criar e retornar um HospedeOut com os dados correspondentes")
    public void criar_quandoReceberHospedeInDeveRetornarHospedeOut() {
        HospedeIn hospedeIn = new HospedeIn(
                "João",
                "12345678911",
                "(49) 99988-7766",
                "teste@teste.com"
        );

        HospedeDomain hospedeDomain = HospedeInToHospedeDomainAdapter.inicializa().converte(hospedeIn);

        when(hospedeDomainCommandDataAccess.save(any(HospedeDomain.class))).thenReturn(hospedeDomain);
        HospedeOut resultado = this.hospedeService.criar(hospedeIn);

        verify(this.hospedeDomainCommandDataAccess).save(any(HospedeDomain.class));
        assertNotNull(resultado);
        assertEquals(resultado.nome(),hospedeIn.nome());
        assertEquals(resultado.Documento(),hospedeIn.documento());
        assertEquals(resultado.email(),hospedeIn.email());
        assertEquals(resultado.telefone(),hospedeIn.telefone());
    }
}
