package com.estudodetestes.GerenciadorFinanceiroJunit5.barriga.domain;

import com.estudodetestes.GerenciadorFinanceiroJunit5.domain.builders.ContaBuilder;
import com.estudodetestes.GerenciadorFinanceiroJunit5.domain.builders.UsuarioBuilder;
import com.estudodetestes.GerenciadorFinanceiroJunit5.exceptions.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.estudodetestes.GerenciadorFinanceiroJunit5.domain.builders.ContaBuilder.umaConta;
import static com.estudodetestes.GerenciadorFinanceiroJunit5.domain.builders.UsuarioBuilderOld.umUsuario;

public class ContaTest {

    @Test
    public void deveCriarContaValida(){
        //criar a conta
        Conta conta = umaConta().agora();
        //fazer assertivas
        Assertions.assertAll("Conta",
                () -> Assertions.assertEquals(1L, conta.id()),
                () -> Assertions.assertEquals("Conta valida", conta.nome()),
                () -> Assertions.assertEquals(umUsuario().agora(), conta.usuario()));
    }

    /**
     * No Junit5 o parameterized não precisa estar em uma classe separada
     */
    @ParameterizedTest
    @MethodSource(value = "dataProvider")
    public void deveRejeitarContaInvalida(Long id, String nome, Usuario usuario, String mensagem){
        String errorMessage = Assertions.assertThrows(ValidationException.class, () ->
                umaConta().comId(id).comNome(nome).comUsuario(usuario).agora()).getMessage();
        Assertions.assertEquals(mensagem, errorMessage);
    }

    private static Stream<Arguments> dataProvider(){
        return Stream.of(
                Arguments.of(1l, null, UsuarioBuilder.umUsuario().agora(), "Nome é obrigatório"),
                Arguments.of(1l, "Uma conta válid", null, "Usuário é obrigatório")
        );
    }
}
