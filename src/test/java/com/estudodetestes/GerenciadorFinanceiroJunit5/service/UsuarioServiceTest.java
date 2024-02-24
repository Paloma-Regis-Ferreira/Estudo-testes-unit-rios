package com.estudodetestes.GerenciadorFinanceiroJunit5.service;

import com.estudodetestes.GerenciadorFinanceiroJunit5.barriga.domain.Usuario;
import com.estudodetestes.GerenciadorFinanceiroJunit5.exceptions.ValidationException;
import com.estudodetestes.GerenciadorFinanceiroJunit5.service.repositories.UsuarioRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.estudodetestes.GerenciadorFinanceiroJunit5.domain.builders.UsuarioBuilder.umUsuario;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @InjectMocks
    //não é um mock pois é a classe usada, mas é onde todos os mocks são injetados
    private UsuarioService service;

    @Mock
    private UsuarioRepository repository;

    @BeforeEach
    public void setup(){
        /* necessario quando não tem o @ExtendWith para inicializar os mock :
           MockitoAnnotations.openMocks(this); */

        /* Uma forma de inicializar os mocks sem as anotações:
           repository = mock(UsuarioRepository.class);
           service = new UsuarioService(repository); */
    }

    @AfterEach
    public void tearDown(){
        //garante que não ocorreu mais nenhuma interação com o mock alem das especificadas anteriormente, no caso o times especifica uma entao qualquer outra causaria erro
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void deveRetornarEmptyQuandoUsuarioInexistente(){
        when(service.getUserByEmail("mail@mail.com")).thenReturn(Optional.empty());

        Optional<Usuario> user = service.getUserByEmail("mail@mail.com");
        assertTrue(user.isEmpty());
        verify(repository, times(1)).getByEmail("mail@mail.com");
    }

    @Test
    public void deveRetornarUsuarioPorEmail(){
        when(service.getUserByEmail("mail@mail.com")).thenReturn(Optional.of(umUsuario().agora()));

        Optional<Usuario> user = service.getUserByEmail("mail@mail.com");
        assertTrue(user.isPresent());

        //foi invocado exatamente uma vez
        verify(repository, times(1)).getByEmail("mail@mail.com");
        //no minimo uma vez
        verify(repository, atLeastOnce()).getByEmail("mail@mail.com");
        //no minimo 5 vezes
//        verify(repository, atLeast(5)).getByEmail("mail@mail.com");
        //nunca aconteceu
        verify(repository, never()).getByEmail("qualquer@mail.com");

    }

    @Test
    public void deveRetornarUsuarioPorEmailExemploDeMock(){
        when(service.getUserByEmail("mail@mail.com")).thenReturn(Optional.of(umUsuario().agora()));

        Optional<Usuario> user = service.getUserByEmail("mail@mail.com");
        assertTrue(user.isPresent());

        //foi invocado exatamente uma vez
        verify(repository, times(1)).getByEmail("mail@mail.com");
        //no minimo uma vez
        verify(repository, atLeastOnce()).getByEmail("mail@mail.com");
        //no minimo 5 vezes
//        verify(repository, atLeast(5)).getByEmail("mail@mail.com");
        //nunca aconteceu
        verify(repository, never()).getByEmail("qualquer@mail.com");
    }

    @Test
    public void deveSalvarUsuarioComSucesso(){
        Usuario usuarioParaSalvar = umUsuario().comId(null).agora();

        when(repository.getByEmail(usuarioParaSalvar.getEmail()))
                .thenReturn(Optional.empty());
        when(repository.salvar(usuarioParaSalvar)).thenReturn(umUsuario().agora());

        /*quando é definida uma expectativa no when passando um objeto especifico para o metodo que sera executado,
        devemos garantir que o mesmo objeto (de acordo com o equals e hashcode) deve ser passado no metodo a ser executado
        ou gerará falha pois eles estará esperando uma coisa e receberá outra e não saberá identificar o que deve ser feito.
        Esse é um ponto importante quando, por exemplo, após a execução do método ocorra modificações no objeto que o "when" não esteja esperando
         */
        Usuario usuarioSalvo = service.salvar(usuarioParaSalvar);
        assertNotNull(usuarioSalvo.getId());

        verify(repository).getByEmail(usuarioParaSalvar.getEmail());
        verify(repository).salvar(usuarioParaSalvar);
    }

    @Test
    public void deveRejeitarUsuarioExistente(){
        Usuario usuarioParaSalvar = umUsuario().comId(null).agora();

        when(repository.getByEmail(usuarioParaSalvar.getEmail())).thenReturn(Optional.of(umUsuario().agora()));

        ValidationException ex = Assertions.assertThrows(ValidationException.class, () -> service.salvar(usuarioParaSalvar));

        assertEquals("Usuario user@email.com já cadastrado!", ex.getMessage());
        //assertiva util quando existe uma variavel dentro da mensagem, principalmente quando existir um timestamp:
        assertTrue(ex.getMessage().endsWith("já cadastrado!"));
        verify(repository, Mockito.never()).salvar(usuarioParaSalvar);
    }
}
