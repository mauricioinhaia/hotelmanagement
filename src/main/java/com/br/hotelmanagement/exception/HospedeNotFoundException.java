package com.br.hotelmanagement.exception;

public class HospedeNotFoundException extends RuntimeException {

    private final String source;

    public HospedeNotFoundException(String message, String source) {
        super(message);
        this.source = source;
    }

    public String getSource() {
        return source;
    }
}
