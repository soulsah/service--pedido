package br.com.fiap.postech.service_pedido.service.impl;

import br.com.fiap.postech.service_pedido.service.SnsService;
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
    public String sendSnsMessage(String message,String topicArn) {
        PublishRequest request = PublishRequest.builder()
                .topicArn(topicArn)
                .message(message)
                .build();

        PublishResponse result = snsClient.publish(request);
        return result.messageId();
    }
    
}
