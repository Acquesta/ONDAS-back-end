package br.com.ondas.identidade.infrastructure.persistence.usuario;

import br.com.ondas.identidade.domain.enums.PerfilTipo;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "usuarios")
public class UsuarioJpaEntity {
    @Id
    private UUID id;

    @Column(nullable = false)
    private String nome;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String cpf;

    @Column(nullable = false)
    private String senha;

    private String especialidade;

    @Enumerated(EnumType.STRING)
    private PerfilTipo perfilTipo;

    @Column(name = "gateway_customer_id", unique = true)
    private String gatewayCustomerId;

    protected UsuarioJpaEntity(){};

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public String getEspecialidade() { return especialidade; }
    public void setEspecialidade(String especialidade) { this.especialidade = especialidade; }

    public PerfilTipo getPerfilTipo() { return perfilTipo; }
    public void setPerfilTipo(PerfilTipo perfilTipo) { this.perfilTipo = perfilTipo; }

    public String getGatewayCustomerId() { return gatewayCustomerId; }

    public void setGatewayCustomerId(String gatewayCustomerId) { this.gatewayCustomerId = gatewayCustomerId; }
}
