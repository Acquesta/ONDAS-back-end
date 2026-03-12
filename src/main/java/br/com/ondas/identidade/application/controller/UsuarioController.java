package br.com.ondas.identidade.application.controller;

import br.com.ondas.identidade.domain.entity.Usuario;
import br.com.ondas.identidade.domain.repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioController(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping
    public ResponseEntity<Usuario> criar(@RequestBody CriarUsuarioRequest request) {
        // Validação básica
        if (request.nome() == null || request.email() == null || request.cpf() == null || request.perfil() == null || request.senha() == null) {
            return ResponseEntity.badRequest().build();
        }

        // TODO: Criptografar a senha com BCrypt antes de salvar
        String senhaCriptografada = passwordEncoder.encode(request.senha());

        Usuario novoUsuario = Usuario.criar(
                request.nome(),
                request.email(),
                request.cpf(),
                request.perfil(),
                senhaCriptografada
        );

        usuarioRepository.salvar(novoUsuario);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(novoUsuario.getId())
                .toUri();

        return ResponseEntity.created(uri).body(novoUsuario);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable UUID id) {
        return usuarioRepository.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
