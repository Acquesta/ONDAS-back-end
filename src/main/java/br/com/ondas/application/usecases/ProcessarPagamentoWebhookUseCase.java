package br.com.ondas.application.usecases;

import br.com.ondas.application.dto.AtualizarStatusPagamentoCommand;
import br.com.ondas.domain.entity.Assinatura;
import br.com.ondas.domain.repository.AssinaturaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ProcessarPagamentoWebhookUseCase {
    private final AssinaturaRepository assinaturaRepository;

    // A injeção de dependência via construtor é a mais recomendada no Spring
    public ProcessarPagamentoWebhookUseCase(AssinaturaRepository assinaturaRepository) {
        this.assinaturaRepository = assinaturaRepository;
    }

    @Transactional
    public void executar(AtualizarStatusPagamentoCommand comando) {

        // 1. Busca a assinatura do aluno no banco (via interface do domínio)
        Assinatura assinatura = assinaturaRepository.buscarPorUsuarioId(comando.usuarioId())
                .orElseThrow(() -> new IllegalArgumentException("Assinatura não encontrada para este usuário."));

        // 2. Orquestra a regra de negócio baseada no retorno do gateway
        if (comando.pagamentoAprovado()) {
            // Chama a Entidade Rica para aplicar a regra (ela garante que o status mude para ATIVA)
            assinatura.ativar();
        } else {
            // Se o cartão recusar ou atrasar, a entidade aplica a regra de inadimplência
            assinatura.marcarComoAtrasada();
        }

        // 3. Salva o novo estado no banco de dados
        assinaturaRepository.salvar(assinatura);
    }
}
