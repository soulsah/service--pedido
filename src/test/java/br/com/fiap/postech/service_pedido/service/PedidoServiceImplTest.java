package br.com.fiap.postech.service_pedido.service;

import br.com.fiap.postech.service_pedido.entity.Pedido;
import br.com.fiap.postech.service_pedido.entity.ProdutoItem;
import br.com.fiap.postech.service_pedido.exception.PedidoNotFoundException;
import br.com.fiap.postech.service_pedido.records.PedidoRecord;
import br.com.fiap.postech.service_pedido.records.ProdutoItemRecord;
import br.com.fiap.postech.service_pedido.repository.PedidoRepository;
import br.com.fiap.postech.service_pedido.service.impl.PedidoServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PedidoServiceImplTest {
    @Mock
    private PedidoRepository pedidoRepository;


    @InjectMocks
    private PedidoServiceImpl pedidoService;

    public PedidoServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAllPedidosSuccess() throws PedidoNotFoundException {
        Pedido pedido = new Pedido("1L", 123L,
                List.of(new ProdutoItem(101L, 2)), 100.50, "Em processamento");
        PedidoRecord pedidoRecord = new PedidoRecord("1L", 123L,
                List.of(new ProdutoItemRecord(101L, 2)), 100.50, "Em processamento");
        List<Pedido> pedidos = List.of(pedido);
        when(pedidoRepository.findAll()).thenReturn(pedidos);

        List<PedidoRecord> result = pedidoService.findAllPedidos();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(pedidoRepository, times(1)).findAll();
    }

    @Test
    void testFindAllPedidosEmpty() {
        when(pedidoRepository.findAll()).thenReturn(List.of());

        PedidoNotFoundException exception = assertThrows(PedidoNotFoundException.class, () -> pedidoService.findAllPedidos());

        assertNotNull(exception);
        verify(pedidoRepository, times(1)).findAll();
    }

    @Test
    void testFindPedidoByIdSuccess() throws PedidoNotFoundException {
        String id = "1";
        Pedido pedido = new Pedido("1L", 123L,
                List.of(new ProdutoItem(101L, 2)), 100.50, "Em processamento");
        PedidoRecord pedidoRecord = new PedidoRecord("1L", 123L,
                List.of(new ProdutoItemRecord(101L, 2)), 100.50, "Em processamento");
        when(pedidoRepository.findById(id)).thenReturn(Optional.of(pedido));

        PedidoRecord result = pedidoService.findPedidoById(id);

        assertNotNull(result);
        assertEquals(pedidoRecord, result);
        verify(pedidoRepository, times(1)).findById(id);
    }

    @Test
    void testFindPedidoByIdNotFound() {
        String id = "1";
        when(pedidoRepository.findById(id)).thenReturn(Optional.empty());

        PedidoNotFoundException exception = assertThrows(PedidoNotFoundException.class, () -> pedidoService.findPedidoById(id));

        assertNotNull(exception);
        verify(pedidoRepository, times(1)).findById(id);
    }



    @Test
    void testDeletePedido() {
        String id = "1";

        pedidoService.deletePedido(id);

        verify(pedidoRepository, times(1)).deleteById(id);
    }
}
