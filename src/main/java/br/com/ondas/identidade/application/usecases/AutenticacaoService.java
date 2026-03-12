package br.com.ondas.identidade.application.usecases;

import br.com.ondas.identidade.domain.entity.Usuario;
import br.com.ondas.identidade.domain.repository.UsuarioRepository;
import br.com.ondas.identidade.infrastructure.security.UsuarioUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AutenticacaoService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public AutenticacaoService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> usuarioOp = usuarioRepository.buscarPorEmail(username);

        if (usuarioOp.isEmpty()) {
            throw new UsernameNotFoundException("Usuário não encontrado para o email: " + username);
        }

        return new UsuarioUserDetails(usuarioOp.get());
    }
}
