package br.com.ondas.infrastructure.persistence;

import br.com.ondas.domain.entity.Assinatura;
import br.com.ondas.domain.enums.TipoPlano;
import br.com.ondas.domain.repository.AssinaturaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class AssinaturaRepositoryImpl implements AssinaturaRepository {
    private final SpringDataAssinaturaDao springDao;

    public AssinaturaRepositoryImpl(SpringDataAssinaturaDao springDao) {
        this.springDao = springDao;
    }

    @Override
    public void salvar(Assinatura assinatura) {
        // Converte a Entidade Rica (Domínio) para a Entidade Burra (JPA)
        AssinaturaJpaEntity jpaEntity = new AssinaturaJpaEntity();
        jpaEntity.setId(assinatura.getId());
        jpaEntity.setUsuarioId(assinatura.getUsuarioId());
        jpaEntity.setTipoPlano(assinatura.getTipoPlano().name());
        jpaEntity.setStatus(assinatura.getStatus().name());
        jpaEntity.setDataExpiracao(assinatura.getDataExpiracao());

        // Salva no Supabase
        springDao.save(jpaEntity);
    }

    @Override
    public Optional<Assinatura> buscarPorUsuarioId(UUID usuarioId) {
        // Busca no banco e, se achar, "remonta" a Entidade Rica do Domínio
        return springDao.findByUsuarioId(usuarioId).map(jpa -> {
            // Nota: Na vida real usaríamos reflexão ou um construtor especial
            // no Domínio para recriar a entidade já existente sem gerar um novo ID.
            return Assinatura.criarNova(
                    jpa.getUsuarioId(),
                    TipoPlano.valueOf(jpa.getTipoPlano()),
                    jpa.getDataExpiracao()
            );
        });
    }

}
