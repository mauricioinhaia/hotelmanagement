package com.br.hotelmanagement.service.reserva;

import com.br.hotelmanagement.dataaccess.command.ReservaDomainCommandDataAccess;
import com.br.hotelmanagement.dataaccess.query.ReservaDomainQueryDataAccess;
import com.br.hotelmanagement.exception.DataAccessException;
import com.br.hotelmanagement.exception.ReservaNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReservaServiceTest {

    @Mock
    private ReservaDomainQueryDataAccess reservaDomainQueryDataAccess;
    @Mock
    private ReservaDomainCommandDataAccess reservaDomainCommandDataAccess;

    @InjectMocks
    private ReservaService reservaService;

    @Test
    @DisplayName("QUANDO receber um id inválido DEVE lançar uma exceção ReservaNotFoundException")
    public void buscarPorId_quandoReceberIdInvalido_deveLancarReservaNotFoundException(){
        Long idInvalido = 999L;
        String mensagemErro = "Reserva não encontrada";
        String execucao = "findById";

        when(this.reservaDomainQueryDataAccess.findById(idInvalido))
                .thenThrow(new DataAccessException(mensagemErro, execucao));

        ReservaNotFoundException reservaNotFoundException = assertThrows(
                ReservaNotFoundException.class,
                () -> this.reservaService.buscarPorId(idInvalido)
        );

        verify(this.reservaDomainQueryDataAccess).findById(idInvalido);
        assertEquals(mensagemErro, reservaNotFoundException.getMessage());
        assertEquals(execucao, reservaNotFoundException.getSource());
    }

    @Test
    @DisplayName("QUANDO receber um id inválido DEVE lançar uma exceção ReservaNotFoundException ao tentar deletar")
    public void deletar_quandoReceberIdInvalido_deveLancarReservaNotFoundException() {
        Long idInvalido = 999L;
        String mensagemErro = "Reserva não encontrada";
        String execucao = "findById";

        when(this.reservaDomainQueryDataAccess.findById(idInvalido))
                .thenThrow(new DataAccessException(mensagemErro, execucao));

        ReservaNotFoundException excecao = assertThrows(
                ReservaNotFoundException.class,
                () -> this.reservaService.deletar(idInvalido)
        );

        verify(this.reservaDomainQueryDataAccess).findById(idInvalido);
        assertEquals(mensagemErro, excecao.getMessage());
        assertEquals(execucao, excecao.getSource());
    }
}
