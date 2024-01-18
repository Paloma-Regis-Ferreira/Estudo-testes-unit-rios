package com.estudodetestes.GerenciadorFinanceiroJunit5.domain.builders;

import com.estudodetestes.GerenciadorFinanceiroJunit5.barriga.domain.Usuario;

public class UsuarioBuilderOld {

    private Long id;
    private String nome;
    private String email;
    private String senha;
    
    /**
     * construtor privado para ter controle do acesso de criação do builder
     */
    private UsuarioBuilderOld(){}
    
    public static UsuarioBuilderOld umUsuario(){
        UsuarioBuilderOld builder = new UsuarioBuilderOld();
        inicializarDadosPadroes(builder);
        return builder;
    }

    private static void inicializarDadosPadroes(UsuarioBuilderOld builder) {
        builder.id = 1L;
        builder.nome = "Usuario valido";
        builder.email = "user@email.com";
        builder.senha = "123456";
    }

    public UsuarioBuilderOld comId (Long param){
        id = param;
        return this;
    }

    public UsuarioBuilderOld comNome (String param){
        nome = param;
        return this;
    }

    public UsuarioBuilderOld comEmail (String param){
        email = param;
        return this;
    }

    public UsuarioBuilderOld comSenha (String param){
        senha = param;
        return this;
    }

    public Usuario agora(){
        return new Usuario(id, nome, email, senha);
    }
}
