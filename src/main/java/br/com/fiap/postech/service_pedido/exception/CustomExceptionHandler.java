package br.com.fiap.postech.service_pedido.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(PedidoNotFoundException.class)
    protected ResponseEntity<Object> resourceNotFound(PedidoNotFoundException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        return getObjectResponseEntity(request, status, e.getMessage(), e);
    }

    private ResponseEntity<Object> getObjectResponseEntity(HttpServletRequest request, HttpStatus status, String message, Exception e) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", Instant.now());
        body.put("status", status.value());
        body.put("error", message);
        body.put("path", request.getRequestURI());
        return new ResponseEntity<>(body, status);
    }
}
