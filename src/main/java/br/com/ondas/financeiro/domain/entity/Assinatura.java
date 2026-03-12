package br.com.ondas.financeiro.domain.entity;

import br.com.ondas.financeiro.domain.enums.StatusAssinatura;
import br.com.ondas.financeiro.domain.enums.TipoPlano;

import java.time.ZonedDateTime;
import java.util.UUID;

public class Assinatura {
    private UUID id;
    private UUID usuarioId;
    private TipoPlano tipoPlano;
    private StatusAssinatura status;
    private ZonedDateTime dataExpiracao;

    // Construtor privado para forçar a criação através de "Factory Methods"
    private Assinatura(UUID usuarioId, TipoPlano tipoPlano, ZonedDateTime dataExpiracao) {
        this.id = UUID.randomUUID();
        this.usuarioId = usuarioId;
        this.tipoPlano = tipoPlano;
        this.status = StatusAssinatura.PENDENTE; // Toda assinatura nasce pendente até o webhook do gateway confirmar
        this.dataExpiracao = dataExpiracao;
    }

    // Factory Method: Criação oficial de uma nova assinatura
    public static Assinatura criarNova(UUID usuarioId, TipoPlano tipoPlano, ZonedDateTime dataExpiracao) {
        return new Assinatura(usuarioId, tipoPlano, dataExpiracao);
    }

    // Factory Method: Reconstituição do BD (impede perda do ID real)
    public static Assinatura reconstituir(UUID id, UUID usuarioId, TipoPlano tipoPlano, StatusAssinatura status, ZonedDateTime dataExpiracao) {
        Assinatura assinatura = new Assinatura(usuarioId, tipoPlano, dataExpiracao);
        assinatura.id = id;
        assinatura.status = status;
        return assinatura;
    }

    // --- REGRAS DE NEGÓCIO (Comportamentos) ---

    public void ativar() {
        if (this.status == StatusAssinatura.CANCELADA) {
            throw new IllegalStateException("Não é possível ativar uma assinatura que já foi cancelada.");
        }
        this.status = StatusAssinatura.ATIVA;
    }

    public void cancelar() {
        this.status = StatusAssinatura.CANCELADA;
    }

    public void marcarComoAtrasada() {
        if (this.status == StatusAssinatura.ATIVA && isExpirada()) {
            this.status = StatusAssinatura.ATRASADA;
        }
    }

    public boolean isExpirada() {
        if (this.tipoPlano == TipoPlano.UNICO) {
            return false; // Planos únicos/vitalícios nunca expiram
        }
        return dataExpiracao != null && ZonedDateTime.now().isAfter(dataExpiracao);
    }

    // Getters para expor os dados (sem Setters, para que ninguém altere o status "na mão" de fora da classe)
    public UUID getId() { return id; }
    public UUID getUsuarioId() { return usuarioId; }
    public TipoPlano getTipoPlano() { return tipoPlano; }
    public StatusAssinatura getStatus() { return status; }
    public ZonedDateTime getDataExpiracao() { return dataExpiracao; }
}
