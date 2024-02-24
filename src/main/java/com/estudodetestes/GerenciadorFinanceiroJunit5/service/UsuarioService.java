package com.estudodetestes.GerenciadorFinanceiroJunit5.service;

import com.estudodetestes.GerenciadorFinanceiroJunit5.barriga.domain.Usuario;
import com.estudodetestes.GerenciadorFinanceiroJunit5.exceptions.ValidationException;
import com.estudodetestes.GerenciadorFinanceiroJunit5.service.repositories.UsuarioRepository;

import java.util.Optional;

public class UsuarioService {

    private UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public Usuario salvar(Usuario usuario){
        repository.getByEmail(usuario.getEmail()).ifPresent(user -> {
                throw new ValidationException(String.format("Usuario %s já cadastrado!", usuario.getEmail()));
        });
        return repository.salvar(usuario);
    }

    public Optional<Usuario> getUserByEmail(String email){
        return repository.getByEmail(email);
    }
}
