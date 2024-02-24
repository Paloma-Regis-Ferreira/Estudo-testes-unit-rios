package com.estudodetestes.GerenciadorFinanceiroJunit5.infra;

import com.estudodetestes.GerenciadorFinanceiroJunit5.barriga.domain.Usuario;
import com.estudodetestes.GerenciadorFinanceiroJunit5.service.repositories.UsuarioRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsuarioMemoryRepository implements UsuarioRepository {

    private List<Usuario> usuarios;
    private Long currentId;

    public UsuarioMemoryRepository() {
        this.usuarios = new ArrayList<>();
        this.currentId = 0L;
        salvar(new Usuario(null, "User #1", "user1@gmail.com", "12345"));
    }

    @Override
    public Usuario salvar(Usuario usuario) {
        Usuario newUser = new Usuario(nextId(), usuario.getNome(), usuario.getEmail(), usuario.getSenha());
        usuarios.add(newUser);
        return newUser;
    }

    @Override
    public Optional<Usuario> getByEmail(String email) {
        return usuarios.stream()
                .filter(usuario -> usuario.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    private Long nextId(){
        return ++currentId;
    }

    public void printUsers(){
        System.out.println(usuarios);
    }

    public static void main(String[] args) {
        UsuarioMemoryRepository repository = new UsuarioMemoryRepository();
        repository.printUsers();
        repository.salvar(new Usuario(null, "Paloma", "aa@mf", "2345565"));
        repository.salvar(new Usuario(null, "p", "aa@mf", "2345565"));
        repository.printUsers();
    }
}
