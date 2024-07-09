package br.com.fiap.postech.service_pedido.entity;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonProperty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoItem {

    @BsonProperty(value = "id")
    private Long idProduto;

    @NotNull(message = "quantidade não pode ser nula")
    private Integer quantidade;
}
