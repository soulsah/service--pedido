package br.com.fiap.postech.service_pedido.records;

import java.io.Serializable;

public record ProdutoRecord(Long id, String nome, String descricao, double preco, Integer quantidade, String status)implements Serializable {
}
