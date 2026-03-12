package br.com.ondas.financeiro.application.usecases;

import java.util.UUID;

public record AtualizarStatusPagamentoCommand(
        UUID usuarioId,
        boolean pagamentoAprovado
) {}
