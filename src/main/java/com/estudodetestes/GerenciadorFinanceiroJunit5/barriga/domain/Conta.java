package com.estudodetestes.GerenciadorFinanceiroJunit5.barriga.domain;

public class Conta {

    private Long id;
    private String nome;
    private Usuario usuario;

    public Conta(Long id, String nome, Usuario usuario) {
        this.id = id;
        this.nome = nome;
        this.usuario = usuario;
    }


    public long id() {
        return id;
    }

    public String nome() {
        return nome;
    }

    public Usuario usuario() {
        return usuario;
    }
}
