package com.estudodetestes.GerenciadorFinanceiroJunit5.service.repositories;

import com.estudodetestes.GerenciadorFinanceiroJunit5.barriga.domain.Usuario;

import java.util.Optional;

public interface UsuarioRepository {

    Usuario salvar(Usuario usuario);

    Optional<Usuario> getByEmail(String email);
}
