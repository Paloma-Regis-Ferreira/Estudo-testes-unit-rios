package com.estudodetestes.GerenciadorFinanceiroJunit5.infra;

import com.estudodetestes.GerenciadorFinanceiroJunit5.barriga.domain.Usuario;
import com.estudodetestes.GerenciadorFinanceiroJunit5.service.repositories.UsuarioRepository;

import java.util.Optional;

import static com.estudodetestes.GerenciadorFinanceiroJunit5.domain.builders.UsuarioBuilder.umUsuario;

public class UsuarioDummyRepository implements UsuarioRepository {
    @Override
    public Usuario salvar(Usuario usuario) {
        return umUsuario()
                .comNome(usuario.getNome())
                .comEmail(usuario.getEmail())
                .comSenha(usuario.getSenha())
                .agora();
    }

    @Override
    public Optional<Usuario> getByEmail(String email) {
        if ("user@email.com".equalsIgnoreCase(email)){
            return Optional.of(umUsuario().comEmail(email).agora());
        }
        return Optional.empty();
    }

}
