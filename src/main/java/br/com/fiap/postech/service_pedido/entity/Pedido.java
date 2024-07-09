package br.com.fiap.postech.service_pedido.entity;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "pedidos")
    public class Pedido {

        @Id
        private String pedidoId;

        @NotEmpty(message = "clientId não pode estar vazio")
        private Long clienteId;

        @NotEmpty(message = "produtos não pode estar vazio")
        @BsonProperty(value = "produtos")
        private List<ProdutoItem> produtos;

        private Double total;

        private String status;

}
