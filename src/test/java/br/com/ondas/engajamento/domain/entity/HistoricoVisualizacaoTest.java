package br.com.ondas.engajamento.domain.entity;

import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class HistoricoVisualizacaoTest {

    @Test
    void testIniciarAssistir() {
        UUID usuarioId = UUID.randomUUID();
        UUID treinoId = UUID.randomUUID();
        HistoricoVisualizacao historico = HistoricoVisualizacao.iniciarAssistir(usuarioId, treinoId);

        assertEquals(0, historico.getSegundoParado());
        assertFalse(historico.getConcluido());
    }

    @Test
    void testAtualizarProgressoERetornarConclusao() {
        HistoricoVisualizacao historico = HistoricoVisualizacao.iniciarAssistir(UUID.randomUUID(), UUID.randomUUID());

        historico.atualizarProgresso(50, 100);
        assertFalse(historico.getConcluido());

        historico.atualizarProgresso(96, 100);
        assertTrue(historico.getConcluido());
    }

    @Test
    void testAtualizarProgressoDivisaoZeroNaoQuebra() {
        HistoricoVisualizacao historico = HistoricoVisualizacao.iniciarAssistir(UUID.randomUUID(), UUID.randomUUID());
        
        // Se duracao = 0, deve ignorar
        historico.atualizarProgresso(10, 0);
        assertFalse(historico.getConcluido());
    }

    @Test
    void testAvaliarTreinoConcluido() {
        HistoricoVisualizacao historico = HistoricoVisualizacao.iniciarAssistir(UUID.randomUUID(), UUID.randomUUID());
        historico.atualizarProgresso(100, 100); // 100%

        historico.avaliarTreino(5);
        assertEquals(5, historico.getNotaAvaliacao());
    }
    
    @Test
    void testErroAvaliarTreinoNaoConcluido() {
        HistoricoVisualizacao historico = HistoricoVisualizacao.iniciarAssistir(UUID.randomUUID(), UUID.randomUUID());
        historico.atualizarProgresso(10, 100); // 10%

        assertThrows(IllegalStateException.class, () -> historico.avaliarTreino(5));
    }
}
