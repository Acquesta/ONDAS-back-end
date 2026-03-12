package br.com.ondas.identidade.infrastructure.persistence.usuario;

import br.com.ondas.identidade.domain.entity.Usuario;
import br.com.ondas.identidade.domain.enums.PerfilTipo;
import br.com.ondas.identidade.domain.repository.UsuarioRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class UsuarioRepositoryImpl implements UsuarioRepository {

    private final SpringDataUsuarioDao springDao;

    public UsuarioRepositoryImpl(SpringDataUsuarioDao springDao) { this.springDao = springDao; }

    @Override
    public void salvar(Usuario usuario) {
        UsuarioJpaEntity entity = new UsuarioJpaEntity();
        entity.setId(usuario.getId());
        entity.setNome(usuario.getNome());
        entity.setEmail(usuario.getEmail());
        entity.setCpf(usuario.getCpf());
        entity.setEspecialidade(usuario.getEspecialidade());
        entity.setPerfilTipo(usuario.getPerfil());
        entity.setSenha(usuario.getSenha());
        entity.setGatewayCustomerId(usuario.getGatewayCustomerId());

        springDao.save(entity);
    }

    @Override
    public Optional<Usuario> buscarPorId(UUID id) {
        return springDao.findById(id).map(this::toDomain);
    }

    @Override
    public Optional<Usuario> buscarPorEmail(String email) {
        return springDao.findByEmail(email).map(this::toDomain);
    }

    @Override
    public Optional<Usuario> buscarPorCpf(String cpf) {
        return springDao.findByCpf(cpf).map(this::toDomain);
    }

    private Usuario toDomain(UsuarioJpaEntity jpa) {
        return Usuario.reconstituir(
                jpa.getId(),
                jpa.getNome(),
                jpa.getEmail(),
                jpa.getCpf(),
                jpa.getPerfilTipo(),
                jpa.getGatewayCustomerId(),
                jpa.getEspecialidade(),
                jpa.getSenha()
        );
    }
}
