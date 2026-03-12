package br.com.ondas.identidade.application.controller;

import br.com.ondas.identidade.domain.enums.PerfilTipo;

public record CriarUsuarioRequest(
        String nome,
        String email,
        String cpf,
        PerfilTipo perfil,
        String senha
) {}
