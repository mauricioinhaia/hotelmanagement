package com.br.hotelmanagement.exception.handle;

import com.br.hotelmanagement.exception.HospedeNotFoundException;
import com.br.hotelmanagement.exception.PayloadException;
import com.br.hotelmanagement.exception.ReservaNaoPodeSerExcluidaException;
import com.br.hotelmanagement.exception.ReservaNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class HttpExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(HospedeNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleHospedeNotFoundException(HospedeNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(buildError(e.getMessage(), e.getSource()));
    }

    @ExceptionHandler(ReservaNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleReservaNotFoundException(ReservaNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).
                body(buildError(e.getMessage(), e.getSource()));
    }

    @ExceptionHandler(ReservaNaoPodeSerExcluidaException.class)
    public ResponseEntity<Map<String, Object>> handleReservaNaoPodeSerExcluidaException(ReservaNaoPodeSerExcluidaException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(buildError(e.getMessage(), e.getSource()));
    }

    @ExceptionHandler(PayloadException.class)
    public ResponseEntity<Map<String, Object>> handlePayloadException(PayloadException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(buildError(e.getMessage(), e.getSource(), e.getDetails()));
    }

    private static Map<String, Object> buildError(String message, String source) {
        Map<String, Object> resultMap = new LinkedHashMap<>();
        resultMap.put("source", source);
        resultMap.put("message", message);
        return resultMap;
    }

    private static Map<String, Object> buildError(String message, String source, Map<String, Object> details) {
        Map<String, Object> resultMap = new LinkedHashMap<>();
        resultMap.put("source", source);
        resultMap.put("message", message);
        resultMap.put("details", details);
        return resultMap;
    }
}
