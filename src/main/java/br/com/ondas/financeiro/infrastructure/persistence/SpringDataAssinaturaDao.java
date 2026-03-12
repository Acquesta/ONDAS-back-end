package br.com.ondas.financeiro.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SpringDataAssinaturaDao extends JpaRepository<AssinaturaJpaEntity, UUID> {
    // O Spring gera o 'SELECT * FROM assinaturas WHERE usuario_id = ?' sozinho!
    Optional<AssinaturaJpaEntity> findByUsuarioId(UUID usuarioId);
}
