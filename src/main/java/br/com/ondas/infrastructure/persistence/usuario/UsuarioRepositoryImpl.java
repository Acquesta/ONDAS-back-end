package br.com.ondas.infrastructure.persistence.usuario;

import br.com.ondas.domain.entity.Assinatura;
import br.com.ondas.domain.repository.AssinaturaRepository;

import java.util.Optional;
import java.util.UUID;

public class UsuarioRepositoryImpl implements AssinaturaRepository {

    private final SpringDataUsuarioDao springDao;

    public UsuarioRepositoryImpl(SpringDataUsuarioDao springDao) { this.springDao = springDao; }


    @Override
    public void salvar(Assinatura assinatura) {

    }

    @Override
    public Optional<Assinatura> buscarPorUsuarioId(UUID usuarioId) {
        return Optional.empty();
    }
}
