package br.com.ondas.domain.entity;

import java.util.UUID;

public class HistoricoVisualizacao {
    private UUID id;
    private UUID usuarioId;
    private UUID treinoId;
    private Integer segundoParado;
    private Boolean concluido;
    private Integer notaAvaliacao;

    private HistoricoVisualizacao(UUID usuarioId, UUID treinoId) {
        this.id = UUID.randomUUID();
        this.usuarioId = usuarioId;
        this.treinoId = treinoId;
        this.segundoParado = 0;
        this.concluido = false;
    }

    public static HistoricoVisualizacao iniciarAssistir(UUID usuarioId, UUID treinoId) {
        return new HistoricoVisualizacao(usuarioId, treinoId);
    }

    // --- REGRAS DE NEGÓCIO ---

    public void atualizarProgresso(Integer segundoAtual, Integer duracaoTotalTreino) {
        if (this.concluido) return; // Se já terminou, não precisa atualizar progresso

        this.segundoParado = segundoAtual;

        // Regra: Considerar concluído se passou de 95% do vídeo
        double porcentagemAssistida = (double) segundoAtual / duracaoTotalTreino;
        if (porcentagemAssistida >= 0.95) {
            this.concluido = true;
        }
    }

    public void avaliarTreino(Integer nota) {
        if (nota < 1 || nota > 5) {
            throw new IllegalArgumentException("A nota de avaliação deve ser entre 1 e 5 estrelas.");
        }
        if (!this.concluido) {
            // Regra opcional: O aluno só pode avaliar se tiver terminado o vídeo
            throw new IllegalStateException("Você precisa concluir o treino antes de avaliar.");
        }
        this.notaAvaliacao = nota;
    }

    // Getters omitidos por brevidade
}
