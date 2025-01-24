package com.br.hotelmanagement.exception.handle;

import com.br.hotelmanagement.exception.HospedeNotFoundException;
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
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(buildError(e.getMessage(), e.getSource()));
    }

    private static Map<String, Object> buildError(String message, String source) {
        Map<String, Object> resultMap = new LinkedHashMap<>();
        resultMap.put("source", source);
        resultMap.put("message", message);
        return resultMap;
    }
}
