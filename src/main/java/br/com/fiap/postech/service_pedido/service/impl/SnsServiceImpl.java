package br.com.fiap.postech.service_pedido.service.impl;

import br.com.fiap.postech.service_pedido.records.PedidoCompletoRecord;
import br.com.fiap.postech.service_pedido.service.SnsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;

@Service
public class SnsServiceImpl implements SnsService {
    @Autowired
    private  SnsClient snsClient;


    public SnsServiceImpl(SnsClient snsClient) {
        this.snsClient = snsClient;
    }


    @Override
    public String sendSnsMessage(PedidoCompletoRecord pedido) {
        String messageBody = convertPedidoToJson(pedido);
        String topicArn = System.getenv("topic.arn.aws");
        PublishRequest request = PublishRequest.builder()
                .topicArn(topicArn)
                .message(messageBody)
                .build();

        PublishResponse result = snsClient.publish(request);
        return result.messageId();
    }

    public String convertPedidoToJson(PedidoCompletoRecord pedido) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(pedido);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting PedidoCompletoRecord to JSON", e);
        }
    }
    
}
