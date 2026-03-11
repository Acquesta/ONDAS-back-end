package br.com.ondas.application.dto;

import java.util.UUID;

public record AtualizarStatusPagamentoCommand(
        UUID usuarioId,
        boolean pagamentoAprovado
) {}
