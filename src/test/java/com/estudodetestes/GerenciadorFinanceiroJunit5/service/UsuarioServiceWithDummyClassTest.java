package com.estudodetestes.GerenciadorFinanceiroJunit5.service;

import com.estudodetestes.GerenciadorFinanceiroJunit5.barriga.domain.Usuario;
import com.estudodetestes.GerenciadorFinanceiroJunit5.domain.builders.UsuarioBuilder;
import com.estudodetestes.GerenciadorFinanceiroJunit5.infra.UsuarioDummyRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UsuarioServiceWithDummyClassTest {

    private UsuarioService service;

    @Test
    public void deveSalvarUsuarioComSuceso(){
        service = new UsuarioService(new UsuarioDummyRepository());
        Usuario usuario = UsuarioBuilder.umUsuario().comId(null).comEmail("novo@mail.com").agora();
        Usuario usarioSalvo = service.salvar(usuario);
        assertNotNull(usarioSalvo.getId());
    }
}
