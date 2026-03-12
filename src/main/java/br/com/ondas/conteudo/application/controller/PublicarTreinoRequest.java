package br.com.ondas.conteudo.application.controller;

import java.util.UUID;

public record PublicarTreinoRequest(
        UUID professorId,
        String titulo,
        String descricao,
        String categoria,
        String urlS3Cru
) {}
