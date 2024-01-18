package com.estudodetestes.GerenciadorFinanceiroJunit5.domain.builders;

import com.estudodetestes.GerenciadorFinanceiroJunit5.barriga.domain.Conta;
import com.estudodetestes.GerenciadorFinanceiroJunit5.barriga.domain.Usuario;

import static com.estudodetestes.GerenciadorFinanceiroJunit5.domain.builders.UsuarioBuilderOld.umUsuario;

public class ContaBuilder {

    private Long id;
    private String nome;
    private Usuario usuario;

    private ContaBuilder() {
    }

    public static ContaBuilder umaConta(){
        ContaBuilder builder = new ContaBuilder();
        inicializarDadosPadroes(builder);
        return builder;
    }

    public static void inicializarDadosPadroes(ContaBuilder builder) {
        builder.id = 1L;
        builder.nome = "Conta valida";
        builder.usuario = umUsuario().agora();
    }

    public ContaBuilder comId(Long param) {
        this.id = param;
        return this;
    }

    public ContaBuilder comNome(String param) {
        this.nome = param;
        return this;
    }

    public ContaBuilder comUsuario(Usuario param) {
        this.usuario = param;
        return this;
    }

    public Conta agora() {
        return new Conta(id, nome, usuario);
    }
}
