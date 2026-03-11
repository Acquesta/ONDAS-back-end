package br.com.ondas.infrastructure.persistence.usuario;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringDataUsuarioDao extends JpaRepository<UsuarioJpaEntity, UUID> {

}
