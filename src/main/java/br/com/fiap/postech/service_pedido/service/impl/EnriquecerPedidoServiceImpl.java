package br.com.fiap.postech.service_pedido.service.impl;

import br.com.fiap.postech.service_pedido.entity.Pedido;
import br.com.fiap.postech.service_pedido.entity.ProdutoItem;
import br.com.fiap.postech.service_pedido.records.ClienteRecord;
import br.com.fiap.postech.service_pedido.records.PedidoCompletoRecord;
import br.com.fiap.postech.service_pedido.records.ProdutoRecord;
import br.com.fiap.postech.service_pedido.service.EnriquecerPedidoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class EnriquecerPedidoServiceImpl implements EnriquecerPedidoService {

    @Value("${url.produtos}")
    private String urlProduto;

    @Value("${url.clientes}")
    private String urlCliente;


    @Override
    public PedidoCompletoRecord EnriquecerPedido(Pedido pedido) {
        List<ProdutoRecord> produtoRecord = pedido.getProdutos().stream().map(produto -> this.procurarProduto(produto.getIdProduto())).toList();
        ClienteRecord clienteRecord = procurarCliente(pedido.getClienteId());

        return new PedidoCompletoRecord(pedido.getPedidoId(), clienteRecord, produtoRecord, pedido.getTotal(), pedido.getStatus());
    }

    public ProdutoRecord procurarProduto(long produtoId) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForEntity(urlProduto + "/" + produtoId, ProdutoRecord.class).getBody();
    }

    public ClienteRecord procurarCliente(long clienteId) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForEntity(urlCliente + "/" + clienteId, ClienteRecord.class).getBody();
    }

    public Double calcularValorProdutos(Pedido pedido) {
        List<ProdutoRecord> produtoRecord = pedido.getProdutos().stream().map(produto -> this.procurarProduto(produto.getIdProduto())).toList();
        double valorTotal = 0;

        for (ProdutoRecord produto : produtoRecord) {
            for (ProdutoItem produtoItemRecord : pedido.getProdutos()) {
                if (produto.id().longValue() == produtoItemRecord.getIdProduto().longValue())
                    valorTotal += produto.preco() * produtoItemRecord.getQuantidade();
            }
        }

        return valorTotal;
    }

}
