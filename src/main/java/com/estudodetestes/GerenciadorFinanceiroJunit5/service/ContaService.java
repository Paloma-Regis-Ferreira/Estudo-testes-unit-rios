package com.estudodetestes.GerenciadorFinanceiroJunit5.service;

import com.estudodetestes.GerenciadorFinanceiroJunit5.barriga.domain.Conta;
import com.estudodetestes.GerenciadorFinanceiroJunit5.exceptions.ValidationException;
import com.estudodetestes.GerenciadorFinanceiroJunit5.service.external.ContaEvent;
import com.estudodetestes.GerenciadorFinanceiroJunit5.service.repositories.ContaRepository;

import java.time.LocalDateTime;
import java.util.List;

public class ContaService implements ContaRepository {

    private ContaRepository repository;
    private ContaEvent event;

    public ContaService(ContaRepository repository, ContaEvent event) {
        this.repository = repository;
        this.event = event;
    }

    @Override
    public Conta salvar(Conta conta) {
        List<Conta> contasUsuario = repository.obterContasPorUsuario(conta.usuario().getId());
        contasUsuario.stream().forEach(contaExistente -> {
            if (conta.nome().equalsIgnoreCase(contaExistente.nome())){
                throw new ValidationException("Usuário já possuí uma conta com esse nome");
            }
        });
        System.out.println("conta: " + conta );
        Conta contaPersisitida = repository.salvar(
                new Conta(null,conta.nome() + LocalDateTime.now(), conta.usuario())
        );
        System.out.println("contaPersisitida: " + contaPersisitida);
        try {
            event.disparar(contaPersisitida, ContaEvent.EventType.CREATED);
        } catch (Exception e) {
            repository.deletar(contaPersisitida);
            throw new RuntimeException("Falha na criacao da conta. Tente novamente!");
        }
        return contaPersisitida;
    }

    @Override
    public List<Conta> obterContasPorUsuario(Long idUsuario) {
        return null;
    }

    @Override
    public void deletar(Conta conta) {

    }
}
