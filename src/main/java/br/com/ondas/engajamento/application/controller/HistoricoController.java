package br.com.ondas.engajamento.application.controller;

import br.com.ondas.engajamento.domain.entity.HistoricoVisualizacao;
// O ideal é ter um repositório, mas criaremos apenas o Controller básico para demonstrar a estrutura da fase 3
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/historico")
public class HistoricoController {

    // Repositório e Serviço deveriam ser injetados aqui. 
    // Para simplificar a fase atual que foca nos fix, estamos provendo os endpoints mínimos do DDD
    
    @PostMapping("/iniciar")
    public ResponseEntity<HistoricoVisualizacao> iniciarTreino(@RequestBody IniciarTreinoRequest request) {
        HistoricoVisualizacao historico = HistoricoVisualizacao.iniciarAssistir(
                request.usuarioId(),
                request.treinoId()
        );
        // repository.salvar(historico)
        return ResponseEntity.ok(historico);
    }

    @PutMapping("/{id}/progresso")
    public ResponseEntity<Void> atualizarProgresso(@PathVariable UUID id, @RequestBody AtualizarProgressoRequest request) {
        // busca no banco
        // historico.atualizarProgresso(request.segundoAtual(), request.duracaoTotal());
        // repository.salvar(historico)
        return ResponseEntity.noContent().build();
    }
}
