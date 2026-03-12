package br.com.ondas.financeiro.application.controller;

import br.com.ondas.financeiro.application.usecases.AtualizarStatusPagamentoCommand;
import br.com.ondas.financeiro.application.usecases.ProcessarPagamentoWebhookUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/assinaturas")
public class AssinaturaController {

    private final ProcessarPagamentoWebhookUseCase processarPagamentoUseCase;

    public AssinaturaController(ProcessarPagamentoWebhookUseCase processarPagamentoUseCase) {
        this.processarPagamentoUseCase = processarPagamentoUseCase;
    }

    // Webhook simulado para o AbacatePay
    @PostMapping("/webhook/abacatepay")
    public ResponseEntity<Void> receberWebhookPagamento(@RequestBody WebhookPayload payload) {
        // Na vida real, validar assinatura do webhook
        
        boolean aprovado = "PAID".equalsIgnoreCase(payload.status());
        
        processarPagamentoUseCase.executar(new AtualizarStatusPagamentoCommand(
                payload.usuarioId(),
                aprovado
        ));

        return ResponseEntity.ok().build();
    }
}
