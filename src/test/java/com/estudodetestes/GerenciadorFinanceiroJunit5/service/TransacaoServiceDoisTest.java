package com.estudodetestes.GerenciadorFinanceiroJunit5.service;

import com.estudodetestes.GerenciadorFinanceiroJunit5.barriga.domain.Transacao;
import com.estudodetestes.GerenciadorFinanceiroJunit5.service.repositories.TransacaoDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

import static com.estudodetestes.GerenciadorFinanceiroJunit5.domain.builders.TransacaoBuilder.umaTransacao;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class TransacaoServiceDoisTest {

    @InjectMocks
    @Spy
    //executa o metodo real
    private TransacaoService service;
    @Mock
    private TransacaoDAO dao;

    @BeforeEach
    public void init(){
        when(service.getTime()).thenReturn(LocalDateTime.of(2023, 1, 29, 8, 30, 28));
    }

    @Test
    public void deveSalvarTransacaoValida(){
        Transacao transacaoParaSalvar = umaTransacao().comId(null).agora();
        Transacao transacaoPersistida = umaTransacao().agora();
        when(dao.salvar(transacaoParaSalvar)).thenReturn(umaTransacao().agora());

        Transacao transacaoSalva = service.salvarTres(transacaoParaSalvar);

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
    }

    @Test
    public void deveRejeitarTransacaoSemValor() throws NoSuchMethodException {

        Transacao transacaoParaSalvar = umaTransacao().comValor(null).agora();

        Method method = TransacaoService.class.getDeclaredMethod("validarMetodoPrivate", Transacao.class);
        method.setAccessible(true);
        Exception exception = assertThrows(Exception.class, () ->
                method.invoke(new TransacaoService(), transacaoParaSalvar));

        assertEquals("Valor inexistente", exception.getCause().getMessage());
    }
}
