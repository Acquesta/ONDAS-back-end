package br.com.ondas.identidade.infrastructure.persistence.usuario;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SpringDataUsuarioDao extends JpaRepository<UsuarioJpaEntity, UUID> {
    Optional<UsuarioJpaEntity> findByEmail(String email);
    Optional<UsuarioJpaEntity> findByCpf(String cpf);
}
