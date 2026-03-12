package br.com.ondas.engajamento.application.controller;

import java.util.UUID;

public record IniciarTreinoRequest(UUID usuarioId, UUID treinoId) {}
