package com.br.grupolaz.neocontra.exceptions;

public class ConnectionLimitExceeded extends RuntimeException {
    public ConnectionLimitExceeded(String message) {
        super("The server cannot deal with more than 2 connections. " + message);
    }

    public ConnectionLimitExceeded() {
        super("The server cannot deal with more than 2 connections.");
    }
}
