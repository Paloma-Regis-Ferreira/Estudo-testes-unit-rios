package com.estudodetestes.GerenciadorFinanceiroJunit5.exceptions;

public class ValidationException extends RuntimeException{
    public ValidationException(String message) {
        super(message);
    }
}
