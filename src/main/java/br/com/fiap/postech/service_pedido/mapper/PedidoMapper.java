package br.com.fiap.postech.service_pedido.mapper;

import br.com.fiap.postech.service_pedido.entity.Pedido;
import br.com.fiap.postech.service_pedido.entity.ProdutoItem;
import br.com.fiap.postech.service_pedido.records.PedidoRecord;
import br.com.fiap.postech.service_pedido.records.ProdutoItemRecord;

import java.util.List;
import java.util.stream.Collectors;

public class PedidoMapper {

    public static PedidoRecord mapToRecord(Pedido pedido) {
        return new PedidoRecord(
                pedido.getPedidoId(),
                pedido.getClienteId(),
                pedido.getProdutos().stream().map(PedidoMapper::mapToProdutoItemRecords).collect(Collectors.toList()),
                pedido.getTotal(),
                pedido.getStatus()
        );
    }

    public static ProdutoItemRecord mapToProdutoItemRecords(ProdutoItem produtoItem) {
        return new ProdutoItemRecord(produtoItem.getIdProduto(),produtoItem.getQuantidade());
    }

    public static ProdutoItem mapFromProdutoItemRecords(ProdutoItemRecord produtoItem) {
        return new ProdutoItem(produtoItem.id(),produtoItem.quantidade());
    }

    public static Pedido mapFromRecord(PedidoRecord pedidoRecord) {
        return new Pedido(
                pedidoRecord.pedidoId(),
                pedidoRecord.clienteId(),
                pedidoRecord.produtosIds().stream().map(PedidoMapper::mapFromProdutoItemRecords).collect(Collectors.toList()),
                pedidoRecord.total(),
                pedidoRecord.status()
        );
    }

}
