package br.com.ondas.infrastructure.persistence;

import jakarta.persistence.*;

import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "assinaturas")
public class AssinaturaJpaEntity {
    @Id
    private UUID id;

    @Column(name = "usuario_id", nullable = false)
    private UUID usuarioId;

    @Column(name = "tipo_plano", nullable = false)
    @Enumerated(EnumType.STRING)
    private String tipoPlano;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private String status;

    @Column(name = "data_expiracao")
    private ZonedDateTime dataExpiracao;

    // Construtor vazio obrigatório para o JPA (Hibernate)
    protected AssinaturaJpaEntity() {}

    // Getters e Setters gerados para o Spring Data manipular os dados
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getUsuarioId() { return usuarioId; }
    public void setUsuarioId(UUID usuarioId) { this.usuarioId = usuarioId; }

    public String getTipoPlano() { return tipoPlano; }
    public void setTipoPlano(String tipoPlano) { this.tipoPlano = tipoPlano; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public ZonedDateTime getDataExpiracao() { return dataExpiracao; }
    public void setDataExpiracao(ZonedDateTime dataExpiracao) { this.dataExpiracao = dataExpiracao; }
}
