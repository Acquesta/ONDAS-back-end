package br.com.ondas.financeiro.domain.entity;

import br.com.ondas.financeiro.domain.enums.StatusAssinatura;
import br.com.ondas.financeiro.domain.enums.TipoPlano;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AssinaturaTest {

    @Test
    void testCriarNovaAssinatura() {
        UUID usuarioId = UUID.randomUUID();
        Assinatura assinatura = Assinatura.criarNova(usuarioId, TipoPlano.RECORRENTE, ZonedDateTime.now().plusYears(1));

        assertNotNull(assinatura.getId());
        assertEquals(usuarioId, assinatura.getUsuarioId());
        assertEquals(TipoPlano.RECORRENTE, assinatura.getTipoPlano());
        assertEquals(StatusAssinatura.PENDENTE, assinatura.getStatus());
        assertNotNull(assinatura.getDataExpiracao());
    }

    @Test
    void testAtivarAssinaturaPendente() {
        Assinatura assinatura = Assinatura.criarNova(UUID.randomUUID(), TipoPlano.RECORRENTE, ZonedDateTime.now().plusMonths(1));
        assinatura.ativar();

        assertEquals(StatusAssinatura.ATIVA, assinatura.getStatus());
    }

    @Test
    void testNaoAtivarAssinaturaCancelada() {
        Assinatura assinatura = Assinatura.criarNova(UUID.randomUUID(), TipoPlano.RECORRENTE, ZonedDateTime.now().plusMonths(1));
        assinatura.cancelar(); // Muda para CANCELADA

        assertThrows(IllegalStateException.class, assinatura::ativar);
    }

    @Test
    void testMarcarComoAtrasadaSeExpirada() {
        Assinatura assinatura = Assinatura.reconstituir(
                UUID.randomUUID(), UUID.randomUUID(), TipoPlano.RECORRENTE,
                StatusAssinatura.ATIVA, ZonedDateTime.now().minusDays(5) // Já expirou
        );

        assertTrue(assinatura.isExpirada());
        assinatura.marcarComoAtrasada();
        assertEquals(StatusAssinatura.ATRASADA, assinatura.getStatus());
    }

    @Test
    void testNaoMarcarComoAtrasadaSeNaoExpirada() {
        Assinatura assinatura = Assinatura.reconstituir(
                UUID.randomUUID(), UUID.randomUUID(), TipoPlano.RECORRENTE,
                StatusAssinatura.ATIVA, ZonedDateTime.now().plusDays(25) // Não expirou
        );

        assertFalse(assinatura.isExpirada());
        assinatura.marcarComoAtrasada();
        assertEquals(StatusAssinatura.ATIVA, assinatura.getStatus()); // Continua ATIVA
    }

    @Test
    void testPlanoVitalicioNuncaExpira() {
        Assinatura assinatura = Assinatura.criarNova(UUID.randomUUID(), TipoPlano.UNICO, ZonedDateTime.now().minusYears(10));
        assertFalse(assinatura.isExpirada());
    }
}
