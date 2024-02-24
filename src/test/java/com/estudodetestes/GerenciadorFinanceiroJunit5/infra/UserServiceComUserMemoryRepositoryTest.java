package com.estudodetestes.GerenciadorFinanceiroJunit5.infra;

import com.estudodetestes.GerenciadorFinanceiroJunit5.barriga.domain.Usuario;
import com.estudodetestes.GerenciadorFinanceiroJunit5.exceptions.ValidationException;
import com.estudodetestes.GerenciadorFinanceiroJunit5.service.UsuarioService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static com.estudodetestes.GerenciadorFinanceiroJunit5.domain.builders.UsuarioBuilder.umUsuario;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserServiceComUserMemoryRepositoryTest {
    private static UsuarioService service = new UsuarioService(new UsuarioMemoryRepository());

    @Test
    @Order(1)
    public void deveSalvarUsuarioValido(){
        Usuario usuario = service.salvar(umUsuario().comId(null).agora());
        assertNotNull(usuario.getId());
        assertEquals(2L, usuario.getId());
    }

    @Test
    @Order(2)
    public void deveRejeitarUsuarioExistente(){
        ValidationException ex = assertThrows(ValidationException.class,
                () -> service.salvar(umUsuario().comId(null).agora()));

        assertEquals("Usuario user@email.com já cadastrado!", ex.getMessage());
    }
}
