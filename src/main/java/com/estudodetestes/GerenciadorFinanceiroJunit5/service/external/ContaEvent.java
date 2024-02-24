package com.estudodetestes.GerenciadorFinanceiroJunit5.service.external;

import com.estudodetestes.GerenciadorFinanceiroJunit5.barriga.domain.Conta;

public interface ContaEvent {

    enum EventType {CREATED, UPDATED, DELETED}

    void disparar(Conta conta, EventType type) throws Exception;
}
