package br.com.fiap.postech.service_pedido.producer.impl;

import br.com.fiap.postech.service_pedido.producer.PedidoProducer;
import br.com.fiap.postech.service_pedido.records.PedidoCompletoRecord;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PedidoProducerImpl  implements PedidoProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;


    @Override
    public void send(Queue queue,PedidoCompletoRecord pedido) {

        rabbitTemplate.convertAndSend(queue.getName(), pedido);
    }


}
