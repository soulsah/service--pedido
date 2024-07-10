package br.com.fiap.postech.service_pedido.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class CustomExceptionHandlerTest {


    @InjectMocks
    private CustomExceptionHandler customExceptionHandler;

    @Mock
    private HttpServletRequest mockRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testResourceNotFound() {
        String errorMessage = "Pedido nao encontrado";
        PedidoNotFoundException exception = new PedidoNotFoundException();
        when(mockRequest.getRequestURI()).thenReturn("/test/path");

        ResponseEntity<Object> response = customExceptionHandler.resourceNotFound(exception, mockRequest);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(errorMessage, ((Map<String, Object>) response.getBody()).get("error"));
        assertEquals("/test/path", ((Map<String, Object>) response.getBody()).get("path"));
        assertEquals(HttpStatus.NOT_FOUND.value(), ((Map<String, Object>) response.getBody()).get("status"));
        assertTrue(((Map<String, Object>) response.getBody()).containsKey("timestamp"));
    }
}
