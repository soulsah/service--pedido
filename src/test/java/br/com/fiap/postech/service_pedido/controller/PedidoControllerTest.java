package br.com.fiap.postech.service_pedido.controller;

import br.com.fiap.postech.service_pedido.exception.PedidoNotFoundException;
import br.com.fiap.postech.service_pedido.records.PedidoRecord;
import br.com.fiap.postech.service_pedido.records.ProdutoItemRecord;
import br.com.fiap.postech.service_pedido.service.PedidoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PedidoControllerTest {

    @InjectMocks
    private PedidoController pedidoController;

    @Mock
    private PedidoService pedidoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllPedidosSuccess() throws PedidoNotFoundException {
        List<PedidoRecord> pedidos = new ArrayList<>();
        pedidos.add(new PedidoRecord(null,1L, List.of(new ProdutoItemRecord(1l,1)),0,null));
        pedidos.add(new PedidoRecord(null,1L, List.of(new ProdutoItemRecord(1l,1)),0,null));

        when(pedidoService.findAllPedidos()).thenReturn(pedidos);

        ResponseEntity<List<PedidoRecord>> response = pedidoController.getAllPedidos();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pedidos, response.getBody());
    }

    @Test
    public void testGetPedidosByIdNotFound() throws PedidoNotFoundException {
        Long id = 1L;

        when(pedidoService.findPedidoById(id)).thenThrow(new PedidoNotFoundException());

        assertThrows(PedidoNotFoundException.class, () -> pedidoController.getPedidosById(id));
    }

    @Test
    public void testGetPedidosByIdSuccess() throws PedidoNotFoundException {
        Long id = 1L;
        PedidoRecord pedido = new PedidoRecord(id,1L, List.of(new ProdutoItemRecord(1l,1)),0,null);

        when(pedidoService.findPedidoById(id)).thenReturn(pedido);

        ResponseEntity<PedidoRecord> response = pedidoController.getPedidosById(id);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pedido, response.getBody());
    }

    @Test
    public void testDeletePedidoSuccess() {
        Long id = 1L;

        doNothing().when(pedidoService).deletePedido(id);

        pedidoController.deletePedido(id);

        verify(pedidoService, times(1)).deletePedido(id);
    }

    @Test
    public void testCreatePedidoSuccess() {
        PedidoRecord pedido = new PedidoRecord(1l,1L, List.of(new ProdutoItemRecord(1l,1)),0,null);
        ;

        when(pedidoService.save(pedido)).thenReturn(pedido);

        ResponseEntity<PedidoRecord> response = pedidoController.createPedido(pedido);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pedido, response.getBody());
    }




}
