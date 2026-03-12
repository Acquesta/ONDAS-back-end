package br.com.ondas.identidade.domain.repository;

import br.com.ondas.identidade.domain.entity.Usuario;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository {
    void salvar(Usuario usuario);
    Optional<Usuario> buscarPorId(UUID id);
    Optional<Usuario> buscarPorEmail(String email);
    Optional<Usuario> buscarPorCpf(String cpf);
}
