package br.com.ondas.infrastructure.persistence.usuario;

import br.com.ondas.domain.enums.PerfilTipo;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "usuarios")
public class UsuarioJpaEntity {
    @Id
    private UUID id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private String nome;

    @Column(unique = true, nullable = false)
    @Enumerated(EnumType.STRING)
    private String email;

    @Column(unique = true, nullable = false)
    @Enumerated(EnumType.STRING)
    private String cpf;

    @Enumerated(EnumType.STRING)
    private String especialidade;

    @Enumerated(EnumType.STRING)
    private PerfilTipo perfilTipo;

    @Column(unique = true)
    @Enumerated(EnumType.STRING)
    private String gagatewayCustomerId;

    protected UsuarioJpaEntity(){};

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getEspecialidade() { return especialidade; }
    public void setEspecialidade(String especialidade) { this.especialidade = especialidade; }

    public PerfilTipo getPerfilTipo() { return perfilTipo; }
    public void setPerfilTipo(PerfilTipo perfilTipo) { this.perfilTipo = perfilTipo; }

    public String getGagatewayCustomerId() { return gagatewayCustomerId; }

    public void setGagatewayCustomerId(String gagatewayCustomerId) { this.gagatewayCustomerId = gagatewayCustomerId; }
}
