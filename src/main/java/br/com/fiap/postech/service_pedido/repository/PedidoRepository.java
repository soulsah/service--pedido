package br.com.fiap.postech.service_pedido.repository;

import br.com.fiap.postech.service_pedido.entity.Pedido;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface  PedidoRepository extends MongoRepository<Pedido, String> {
}
