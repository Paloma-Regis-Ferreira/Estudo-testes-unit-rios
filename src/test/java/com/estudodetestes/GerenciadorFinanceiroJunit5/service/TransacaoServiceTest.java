package com.estudodetestes.GerenciadorFinanceiroJunit5.service;

import com.estudodetestes.GerenciadorFinanceiroJunit5.barriga.domain.Conta;
import com.estudodetestes.GerenciadorFinanceiroJunit5.barriga.domain.Transacao;
import com.estudodetestes.GerenciadorFinanceiroJunit5.exceptions.ValidationException;
import com.estudodetestes.GerenciadorFinanceiroJunit5.service.external.RelogioService;
import com.estudodetestes.GerenciadorFinanceiroJunit5.service.repositories.TransacaoDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.Stream;

import static com.estudodetestes.GerenciadorFinanceiroJunit5.domain.builders.ContaBuilder.umaConta;
import static com.estudodetestes.GerenciadorFinanceiroJunit5.domain.builders.TransacaoBuilder.umaTransacao;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransacaoServiceTest {

    @InjectMocks
    private TransacaoService service;
    @Mock
    private TransacaoDAO dao;
    @Mock
    private RelogioService relogio;

    @BeforeEach
    public void init(){
        /* não garante q o teste sera executado com sucesso, porem garante que ele só será executado se a condição for verdadeira
          caso seja falso ele irá ignorar os testes que tenham essa validação, como ele esta sendo feito no beforeEach, qualquer teste da classe mesmo que não utilize o service que de fato precisa dessa validação
          metodo isHoraValida abaixo faz a mesma coisa porem depende da anotação @EnabledIf ao inves de Assumptions
         */

        //Assumptions.assumeTrue(LocalDateTime.now().getHour() < 8);
    }

    @Test
    public void deveSalvarTransacaoValida(){
        Transacao transacaoParaSalvar = umaTransacao().comId(null).agora();
        Transacao transacaoPersistida = umaTransacao().agora();
        when(dao.salvar(transacaoParaSalvar)).thenReturn(umaTransacao().agora());

        LocalDateTime dataDesejada = LocalDateTime.of(2023, 1, 29, 8, 30, 28);

        /*
          A classe MockedStatic permite que você faça mocks de métodos estáticos.
          O Mockito.mockStatic() é usado para criar um mock da classe LocalDateTime.
          Isso significa que o comportamento padrão da classe LocalDateTime está sendo substituindo por um comportamento simulado, controlado pelo Mockito.

          Neste exemplo, estamos configurando o método estático now() para retornar a dataDesejada que foi definida anteriormente.
          Isso é um exemplo simplificado, mas demonstra como poderia ser configurado o comportamento do mock.

          É preciso usar a dependencia do mockito-inline para poder usar o mock maker apropriado que aceita mocks estaticos
         */

        try(MockedStatic<LocalDateTime> localDateMock = Mockito.mockStatic(LocalDateTime.class)) {
            localDateMock.when(() -> LocalDateTime.now()).thenReturn(dataDesejada);

            Transacao transacaoSalva = service.salvar(transacaoParaSalvar);

            assertNotNull(transacaoSalva.getId());
            assertEquals(transacaoPersistida, transacaoSalva);
            //Junit5 permite o assertAll encadeado separando os objetos de um objeto complexo composto
            assertAll("Transacao",
                    () -> assertEquals(1L, transacaoSalva.getId()),
                    () -> assertEquals("Transacao Valida", transacaoSalva.getDescricao()),
                    () -> {
                        assertAll("Conta",
                                () -> assertEquals("Conta valida", transacaoSalva.getConta().nome()),
                                () -> {
                                    assertAll("Usuario",
                                            () -> assertEquals("Usuario valido", transacaoSalva.getConta().usuario().getNome()),
                                            () -> assertEquals("123456", transacaoSalva.getConta().usuario().getSenha()));
                                });
                    });
            localDateMock.verify(() -> LocalDateTime.now(), times(1));
        }
    }

    /*
    Exemplo de como sai o relatorio dos AssertsAll alinhados, ele indica o caminho:
    Transacao tem uma falha sendo ela dentro da conta, a conta tem duas falhas sendo uma dentro do usuario e uma nela mesmo

        org.opentest4j.MultipleFailuresError: Transacao (1 failure)
            org.opentest4j.MultipleFailuresError: Conta (2 failures)
            org.opentest4j.AssertionFailedError: expected: <Conta valid> but was: <Conta valida>
            org.opentest4j.MultipleFailuresError: Usuario (1 failure)
            org.opentest4j.AssertionFailedError: expected: <Usuario valid> but was: <Usuario valido>
     */

    @ParameterizedTest(name = "{6}")
    @MethodSource(value = "cenariosObrigatorios")
    public void deveValidarCamposObrigatoriosAoSalvar(Long id, String descricao, Double valor, LocalDate data, Conta conta, Boolean status, String mensagem){
        when(relogio.getHoraAtual()).thenReturn(LocalDateTime.of(2023, 1, 1, 4, 30, 14));

        String message = assertThrows(ValidationException.class, () -> {
            Transacao transacaoParaSalvar = umaTransacao()
                    .comId(id)
                    .comDescricao(descricao)
                    .comValor(valor)
                    .comData(data)
                    .comConta(conta)
                    .comStatus(status).agora();

            service.salvarDois(transacaoParaSalvar);
        }).getMessage();

        assertEquals(mensagem, message);
    }

    static Stream<Arguments> cenariosObrigatorios(){
        return Stream.of(
                Arguments.of(1L, null, 10D, LocalDate.now(), umaConta().agora(), true, "Descrição inexistente"),
                Arguments.of(1L, "Descricao", null, LocalDate.now(), umaConta().agora(), true, "Valor inexistente"),
                Arguments.of(1L, "Descricao", 10D, null, umaConta().agora(), true, "Data inexistente"),
                Arguments.of(1L, "Descricao", 10D, LocalDate.now(), null, true, "Conta inexistente")
        );
    }

    @Test
    public void teste(){
        String mensagem = "qualquer teste a ser executado que não dependa da hora do beforeEach";

        assertNotNull(mensagem);
    }

    @Test
    public void deveSalvarTransacaoValidaNoSalvarDoisComClasseRelogio(){
        Transacao transacaoParaSalvar = umaTransacao().comId(null).agora();
        Transacao transacaoPersistida = umaTransacao().agora();
        when(dao.salvar(transacaoParaSalvar)).thenReturn(umaTransacao().agora());
        when(relogio.getHoraAtual()).thenReturn(LocalDateTime.of(2023, 1, 1, 4, 30, 14));

        Transacao transacaoSalva = service.salvarDois(transacaoParaSalvar);

        assertNotNull(transacaoSalva.getId());
        assertEquals(transacaoPersistida, transacaoSalva);
        assertAll("Transacao",
                () -> assertEquals(1L, transacaoSalva.getId()),
                () -> assertEquals("Transacao Valida", transacaoSalva.getDescricao()),
                () -> {
                    assertAll("Conta",
                            () -> assertEquals("Conta valida", transacaoSalva.getConta().nome()),
                            () -> {
                                assertAll("Usuario",
                                        () -> assertEquals("Usuario valido", transacaoSalva.getConta().usuario().getNome()),
                                        () -> assertEquals("123456", transacaoSalva.getConta().usuario().getSenha()));
                            });
                });
    }

    public static boolean isHoraValida(){
        return LocalDateTime.now().getHour() < 8;
    }
}
