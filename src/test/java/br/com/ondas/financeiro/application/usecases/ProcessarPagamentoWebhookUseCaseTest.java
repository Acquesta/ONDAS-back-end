package br.com.ondas.financeiro.application.usecases;

import br.com.ondas.financeiro.domain.entity.Assinatura;
import br.com.ondas.financeiro.domain.enums.StatusAssinatura;
import br.com.ondas.financeiro.domain.enums.TipoPlano;
import br.com.ondas.financeiro.domain.repository.AssinaturaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProcessarPagamentoWebhookUseCaseTest {

    @Mock
    private AssinaturaRepository assinaturaRepository;

    @InjectMocks
    private ProcessarPagamentoWebhookUseCase useCase;

    @Test
    void testPagamentoAprovadoAtivaAssinatura() {
        UUID usuarioId = UUID.randomUUID();
        Assinatura assinatura = Assinatura.criarNova(usuarioId, TipoPlano.RECORRENTE, ZonedDateTime.now().plusMonths(1));
        
        when(assinaturaRepository.buscarPorUsuarioId(usuarioId)).thenReturn(Optional.of(assinatura));

        AtualizarStatusPagamentoCommand command = new AtualizarStatusPagamentoCommand(usuarioId, true);
        useCase.executar(command);

        assertEquals(StatusAssinatura.ATIVA, assinatura.getStatus());
        verify(assinaturaRepository, times(1)).salvar(assinatura);
    }

    @Test
    void testPagamentoRecusadoAtrasada() {
        UUID usuarioId = UUID.randomUUID();
        // Simulando que está ATIVA, mas a data expirou e recebemos aviso de não pagamento
        Assinatura assinatura = Assinatura.reconstituir(
                UUID.randomUUID(), usuarioId, TipoPlano.RECORRENTE, 
                StatusAssinatura.ATIVA, ZonedDateTime.now().minusDays(1)
        );

        when(assinaturaRepository.buscarPorUsuarioId(usuarioId)).thenReturn(Optional.of(assinatura));

        AtualizarStatusPagamentoCommand command = new AtualizarStatusPagamentoCommand(usuarioId, false);
        useCase.executar(command);

        assertEquals(StatusAssinatura.ATRASADA, assinatura.getStatus());
        verify(assinaturaRepository, times(1)).salvar(assinatura);
    }

    @Test
    void testLancamentoExcecaoUsuarioSemAssinatura() {
        UUID usuarioId = UUID.randomUUID();
        when(assinaturaRepository.buscarPorUsuarioId(usuarioId)).thenReturn(Optional.empty());

        AtualizarStatusPagamentoCommand command = new AtualizarStatusPagamentoCommand(usuarioId, true);

        assertThrows(IllegalArgumentException.class, () -> useCase.executar(command));
        verify(assinaturaRepository, never()).salvar(any());
    }
}
