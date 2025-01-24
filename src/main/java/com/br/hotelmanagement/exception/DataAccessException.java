package com.br.hotelmanagement.exception;

public class DataAccessException extends RuntimeException {

    private final String source;

    public DataAccessException(String message, String source) {
        super(message);
        this.source = source;
    }

    public String getSource() {
        return source;
    }
}
