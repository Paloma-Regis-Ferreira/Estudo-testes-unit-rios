package com.estudodetestes.GerenciadorFinanceiroJunit5.service;

import com.estudodetestes.GerenciadorFinanceiroJunit5.barriga.domain.Conta;
import com.estudodetestes.GerenciadorFinanceiroJunit5.exceptions.ValidationException;
import com.estudodetestes.GerenciadorFinanceiroJunit5.service.external.ContaEvent;
import com.estudodetestes.GerenciadorFinanceiroJunit5.service.repositories.ContaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Arrays;

import static com.estudodetestes.GerenciadorFinanceiroJunit5.domain.builders.ContaBuilder.umaConta;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
//o que faz: ignora os alertas do MockitoExtension que avisa quando temos Stubbings desnecessarios. Uma alternativa é exclui-los
@MockitoSettings(strictness = Strictness.LENIENT)
public class ContaServiceTest {

    @Mock
    ContaRepository repository;
    @Mock
    ContaEvent event;
    @InjectMocks
    ContaService service;

    //usado no verify para verificar o valor real que foi enviado na assinatura do metodo chamado por algum mock. Ele não é utilizado para verificar retornos de métodos.
    @Captor
    private ArgumentCaptor<Conta> contaArgumentCaptor;

    @Test
    public void deveSalvarPrimeiraContaDoUsuarioComSucesso() throws Exception {
        Conta contaParaSalvar = umaConta().comId(null).agora();

        //não precisa do when para a lista de contas porque ele ja tras uma conta vazia por padrao
        when(repository.salvar(any(Conta.class))).thenReturn(umaConta().agora());

        //ele não fará nada no doNothing porque o metodo void não realiza nada nesse contexto, porém ele esta esperando o evento CREATED, se outro fosse lançado, seria lançado erro.
        doNothing().when(event).disparar(umaConta().agora(), ContaEvent.EventType.CREATED);

        Conta contaSalva = service.salvar(contaParaSalvar);

        assertNotNull(contaSalva.id());
        verify(repository).salvar(any());
        verify(repository).salvar(contaArgumentCaptor.capture());
        System.out.println("contaArgumentCaptor" + contaArgumentCaptor.getValue());
        //se for feito algum assert como o abaixo sem fazer o trecho de captura no verify, receberá o seguinte erro: No argument value was captured!
        assertTrue(contaArgumentCaptor.getValue().nome().startsWith("Conta valida"));
    }

    @Test
    public void deveRejeitarContaRepetida(){
        Conta contaParaSalvar = umaConta().comId(null).agora();
        when(repository.obterContasPorUsuario(contaParaSalvar.usuario().getId()))
                .thenReturn(Arrays.asList(umaConta().agora()));

        ValidationException ex = assertThrows(ValidationException.class, () ->
                service.salvar(contaParaSalvar));

        assertEquals("Usuário já possuí uma conta com esse nome", ex.getMessage());
    }

    @Test
    public void deveSalvarSegundaContaDoUsuarioMesmoJaExistindoOutrasContas(){
        Conta contaParaSalvar = umaConta().comId(null).agora();

        when(repository.obterContasPorUsuario(contaParaSalvar.usuario().getId()))
                .thenReturn(Arrays.asList(umaConta().comNome("Outra conta").agora()));
        when(repository.salvar(any(Conta.class))).thenReturn(umaConta().agora());

        Conta contaSalva = service.salvar(contaParaSalvar);

        assertNotNull(contaSalva.id());
    }

    @Test
    public void naoDeveManterContaSemEvento() throws Exception {
        Conta contaParaSalvar = umaConta().comId(null).agora();
        Conta contaSalvaValidacao = umaConta().agora();

        when(repository.salvar(any(Conta.class))).thenReturn(contaSalvaValidacao);
        //garante o lançamento de uma exceção, exige um mock dentro do when e o metodo após o parenteses
        doThrow(new Exception("Falha Catastrofica"))
                .when(event).disparar(contaSalvaValidacao, ContaEvent.EventType.CREATED);

        Exception ex = assertThrows(Exception.class, () ->
                service.salvar(contaParaSalvar));

        assertEquals("Falha na criacao da conta. Tente novamente!", ex.getMessage());
        verify(repository).deletar(contaSalvaValidacao);
    }

}
