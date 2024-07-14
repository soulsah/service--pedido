package br.com.fiap.postech.service_pedido.service;

import br.com.fiap.postech.service_pedido.entity.Pedido;
import br.com.fiap.postech.service_pedido.records.ClienteRecord;
import br.com.fiap.postech.service_pedido.records.PedidoCompletoRecord;
import br.com.fiap.postech.service_pedido.records.ProdutoRecord;

public interface EnriquecerPedidoService {

     PedidoCompletoRecord EnriquecerPedido (Pedido pedido );

    Double calcularValorProdutos(Pedido pedido);

     ProdutoRecord procurarProduto(long produtoId);

     ClienteRecord procurarCliente(long clienteId);
}
