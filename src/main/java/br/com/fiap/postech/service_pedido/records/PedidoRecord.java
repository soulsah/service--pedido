package br.com.fiap.postech.service_pedido.records;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;
import java.util.List;
@Valid
public record PedidoRecord(String pedidoId, @NotNull(message="clientId nao pode estar vazio")Long clienteId, @NotNull(message="produtos  nao pode estar vazio") List<ProdutoItemRecord> produtosIds, double total, String status) implements Serializable {

}
