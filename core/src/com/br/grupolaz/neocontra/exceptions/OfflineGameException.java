package com.br.grupolaz.neocontra.exceptions;

public class OfflineGameException extends RuntimeException {
    public OfflineGameException() {
        super("This method can only be called in online games.");
    }
}
