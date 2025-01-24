package com.br.hotelmanagement.exception;

import java.util.Map;

public class PayloadException extends RuntimeException {

    private final String source;
    private Map<String, Object> details;

    public PayloadException(String message, String source) {
        super(message);
        this.source = source;
    }

    public PayloadException(String message, String source, Map<String, Object> details) {
        super(message);
        this.source = source;
        this.details = details;
    }

    public String getSource() {
        return source;
    }

    public Map<String, Object> getDetails() {
        return details;
    }
}
