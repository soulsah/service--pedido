package br.com.fiap.postech.service_pedido.producer;

import br.com.fiap.postech.service_pedido.records.PedidoCompletoRecord;
import org.springframework.amqp.core.Queue;

public interface PedidoProducer {
    void send(Queue queue, PedidoCompletoRecord pedido);
}

