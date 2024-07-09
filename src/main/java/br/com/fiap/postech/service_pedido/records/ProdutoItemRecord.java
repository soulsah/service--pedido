package br.com.fiap.postech.service_pedido.records;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;



@Valid
public record ProdutoItemRecord(
        @NotNull(message = "id do produto não pode ser nulo") Long id,
        @NotNull(message = "quantidade não pode ser nula") Integer quantidade
) {
}
