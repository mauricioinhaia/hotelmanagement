package com.br.hotelmanagement.exception;

public class ReservaNotFoundException extends RuntimeException{
    private final String source;

    public ReservaNotFoundException(String message, String source) {
        super(message);
        this.source = source;
    }

    public String getSource() {
        return source;
    }
}
