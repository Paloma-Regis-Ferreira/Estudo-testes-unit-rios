package com.estudodetestes.GerenciadorFinanceiroJunit5.service.repositories;

import com.estudodetestes.GerenciadorFinanceiroJunit5.barriga.domain.Transacao;

public interface TransacaoDAO {

    Transacao salvar(Transacao transacao);
}
