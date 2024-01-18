package com.estudodetestes.GerenciadorFinanceiroJunit5.barriga.domain;

import com.estudodetestes.GerenciadorFinanceiroJunit5.domain.builders.ContaBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.estudodetestes.GerenciadorFinanceiroJunit5.domain.builders.UsuarioBuilderOld.umUsuario;

public class ContaTest {

    @Test
    public void deveCriarContaValida(){
        //criar a conta
        Conta conta = ContaBuilder.umaConta().agora();
        //fazer assertivas
        Assertions.assertAll("Conta",
                () -> Assertions.assertEquals(1L, conta.id()),
                () -> Assertions.assertEquals("Conta valida", conta.nome()),
                () -> Assertions.assertEquals(umUsuario().agora(), conta.usuario()));
    }
}
