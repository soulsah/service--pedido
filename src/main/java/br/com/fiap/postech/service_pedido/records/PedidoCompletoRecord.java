package br.com.fiap.postech.service_pedido.records;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record PedidoCompletoRecord(String pedidoId, ClienteRecord clienteId,  List<ProdutoRecord> produtosIds, double total, String status) {

}