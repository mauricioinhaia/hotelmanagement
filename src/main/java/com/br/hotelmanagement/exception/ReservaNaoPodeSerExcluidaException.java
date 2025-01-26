package com.br.hotelmanagement.exception;

public class ReservaNaoPodeSerExcluidaException extends RuntimeException {
    private final String source;

    public ReservaNaoPodeSerExcluidaException(String message, String source) {
        super(message);
        this.source = source;
    }

    public String getSource() {
        return source;
    }
}
