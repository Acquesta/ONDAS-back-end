package br.com.ondas.domain.entity;

import br.com.ondas.domain.enums.PerfilTipo;

import java.util.UUID;

public class Usuario {
    private UUID id;
    private String nome;
    private String email;
    private String cpf;
    private String especialidade;
    private PerfilTipo tipoPerfil;
    private String gatewayCustomerId;

    private Usuario(String nome, String email, String cpf, PerfilTipo tipoPerfil) {
        this.id = UUID.randomUUID();
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.tipoPerfil = tipoPerfil;
    }

    // Factory Method
    public static Usuario criar(String nome, String email, String cpf, PerfilTipo tipoPerfil) {
        // Aqui poderíamos ter validações de formato de email e CPF
        return new Usuario(nome, email, cpf, tipoPerfil);
    }

    // --- REGRAS DE NEGÓCIO ---

    public void definirEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public void vincularGatewayDePagamento(String customerId) {
        if (this.gatewayCustomerId != null) {
            throw new IllegalStateException("Usuário já possui um ID de gateway vinculado.");
        }
        this.gatewayCustomerId = customerId;
    }

    public boolean isProfessor() {
        return this.tipoPerfil == PerfilTipo.PROFESSOR;
    }

    // Getters omitidos por brevidade (id, nome, email, cpf, especialidade, tipoPerfil, gatewayCustomerId)
}
