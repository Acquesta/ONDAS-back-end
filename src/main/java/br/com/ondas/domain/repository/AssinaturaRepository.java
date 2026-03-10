package br.com.ondas.domain.repository;

import br.com.ondas.domain.entity.Assinatura;

import java.util.Optional;
import java.util.UUID;

public interface AssinaturaRepository {
    void salvar(Assinatura assinatura);
    Optional<Assinatura> buscarPorUsuarioId(UUID usuarioId);
}
