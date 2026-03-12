package br.com.ondas.identidade.domain.entity;

import br.com.ondas.identidade.domain.enums.PerfilTipo;

import java.util.UUID;

public class Usuario {
    private UUID id;
    private String nome;
    private String email;
    private String cpf;
    private String especialidade;
    private PerfilTipo perfil; // Changed from tipoPerfil to perfil
    private String senha; // Added senha field
    private String gatewayCustomerId;

    private Usuario(String nome, String email, String cpf, PerfilTipo perfil, String senha) { // Updated constructor
        this.id = UUID.randomUUID();
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.perfil = perfil; // Using perfil
        this.senha = senha; // Setting senha
    }

    // Factory Method
    public static Usuario criar(String nome, String email, String cpf, PerfilTipo perfil, String senha) { // Updated factory method
        // Aqui poderíamos ter validações de formato de email e CPF
        return new Usuario(nome, email, cpf, perfil, senha); // Passing senha
    }

    public static Usuario reconstituir(UUID id, String nome, String email, String cpf, PerfilTipo perfil, String gatewayCustomerId, String especialidade, String senha) { // Updated factory method
        Usuario usuario = new Usuario(nome, email, cpf, perfil, senha); // Passing senha
        usuario.id = id;
        usuario.gatewayCustomerId = gatewayCustomerId;
        usuario.especialidade = especialidade;
        return usuario;
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
        return this.perfil == PerfilTipo.PROFESSOR; // Using perfil
    }

    public UUID getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public PerfilTipo getPerfil() {
        return perfil;
    }

    public String getSenha() {
        return senha;
    }

    public String getGatewayCustomerId() {
        return gatewayCustomerId;
    }

    public void setGatewayCustomerId(String gatewayCustomerId) {
        this.gatewayCustomerId = gatewayCustomerId;
    }
}
