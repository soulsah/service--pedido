package br.com.fiap.postech.service_pedido.mapper;

import br.com.fiap.postech.service_pedido.entity.Pedido;
import br.com.fiap.postech.service_pedido.entity.ProdutoItem;
import br.com.fiap.postech.service_pedido.records.PedidoRecord;
import br.com.fiap.postech.service_pedido.records.ProdutoItemRecord;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class PedidoMapperTest {

    @Test
    public void testMapToRecord() {
        Pedido pedido = new Pedido(1L, 123L,
                List.of(new ProdutoItem(101L, 2)), 100.50, "Em processamento");

        PedidoRecord pedidoRecord = PedidoMapper.mapToRecord(pedido);

        assertEquals(1L, pedidoRecord.pedidoId());
        assertEquals(123L, pedidoRecord.clienteId());
        assertEquals(1, pedidoRecord.produtosIds().size());
        assertEquals(101L, pedidoRecord.produtosIds().get(0).id());
        assertEquals(2, pedidoRecord.produtosIds().get(0).quantidade());
        assertEquals(100.50, pedidoRecord.total());
        assertEquals("Em processamento", pedidoRecord.status());
    }

    @Test
    public void testMapToProdutoItemRecords() {
        ProdutoItem produtoItem = new ProdutoItem(101L, 2);

        ProdutoItemRecord produtoItemRecord = PedidoMapper.mapToProdutoItemRecords(produtoItem);

        assertEquals(101L, produtoItemRecord.id());
        assertEquals(2, produtoItemRecord.quantidade());
    }

    @Test
    public void testMapFromProdutoItemRecords() {
        ProdutoItemRecord produtoItemRecord = new ProdutoItemRecord(101L, 2);

        ProdutoItem produtoItem = PedidoMapper.mapFromProdutoItemRecords(produtoItemRecord);

        assertEquals(101L, produtoItem.getIdProduto());
        assertEquals(2, produtoItem.getQuantidade());
    }

    @Test
    public void testMapFromRecord() {
        PedidoRecord pedidoRecord = new PedidoRecord(1L, 123L,
                List.of(new ProdutoItemRecord(101L, 2)), 100.50, "Em processamento");

        Pedido pedido = PedidoMapper.mapFromRecord(pedidoRecord);

        assertEquals(1L, pedido.getPedidoId());
        assertEquals(123L, pedido.getClienteId());
        assertEquals(1, pedido.getProdutos().size());
        assertEquals(101L, pedido.getProdutos().get(0).getIdProduto());
        assertEquals(2, pedido.getProdutos().get(0).getQuantidade());
        assertEquals(100.50, pedido.getTotal());
        assertEquals("Em processamento", pedido.getStatus());
    }
}
