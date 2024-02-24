package com.estudodetestes.GerenciadorFinanceiroJunit5.service.repositories;

import com.estudodetestes.GerenciadorFinanceiroJunit5.barriga.domain.Conta;

import java.util.List;

public interface ContaRepository {

    Conta salvar(Conta conta);

    List<Conta> obterContasPorUsuario(Long idUsuario);

    void deletar(Conta conta);
}
