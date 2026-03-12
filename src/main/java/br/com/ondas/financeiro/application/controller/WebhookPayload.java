package br.com.ondas.financeiro.application.controller;

import java.util.UUID;

public record WebhookPayload(
        UUID usuarioId,
        String status, // PAID, FAILED, PENDING
        String transactionId
) {}
