package com.estudodetestes.GerenciadorFinanceiroJunit5.domain.builders;
import java.lang.Long;
import java.util.Arrays;
import java.lang.String;
import com.estudodetestes.GerenciadorFinanceiroJunit5.barriga.domain.Usuario;


public class UsuarioBuilder {
    private Long id;
    private String nome;
    private String email;
    private String senha;
    private UsuarioBuilder(){}

    public static UsuarioBuilder umUsuario() {
        UsuarioBuilder builder = new UsuarioBuilder();
        inicializarDadosPadroes(builder);
        return builder;
    }

    public static void inicializarDadosPadroes(UsuarioBuilder builder) {
        builder.id = 1L;
        builder.nome = "Usuario valido";
        builder.email = "user@email.com";
        builder.senha = "123456";
    }

    public UsuarioBuilder comId(Long param) {
        this.id = param;
        return this;
    }

    public UsuarioBuilder comNome(String param) {
        this.nome = param;
        return this;
    }

    public UsuarioBuilder comEmail(String param) {
        this.email =param;
        return this;
    }

    public UsuarioBuilder comSenha(String param) {
        this.senha = param;
        return this;
    }

    public Usuario agora() {
        return new Usuario(id, nome, email, senha);
    }
}