package com.estudodetestes.GerenciadorFinanceiroJunit5.barriga.domain;

import com.estudodetestes.GerenciadorFinanceiroJunit5.exceptions.ValidationException;

import java.util.Objects;

public class Usuario {

    private Long id;
    private String nome;
    private String email;
    private String senha;
    public Usuario(Long id, String nome, String email, String senha) {
        if (nome == null) throw new ValidationException("Nome é obrigatorio");
        if (email == null) throw new ValidationException("Email é obrigatorio");
        if (senha == null) throw new ValidationException("Senha é obrigatorio");
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(nome, usuario.nome) && Objects.equals(email, usuario.email) && Objects.equals(senha, usuario.senha);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, email, senha);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                '}';
    }
}
