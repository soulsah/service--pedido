package br.com.fiap.postech.service_pedido.service;

import br.com.fiap.postech.service_pedido.service.impl.SnsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class SnsServiceImplTest {

    @Mock
    private SnsClient snsClient;

    @InjectMocks
    private SnsServiceImpl snsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSendSnsMessage() {
        String message = "Test message";
        String topicArn = "arn:aws:sns:us-east-1:123456789012:TestTopic";
        String expectedMessageId = "test-message-id";

        PublishResponse publishResponse = PublishResponse.builder()
                .messageId(expectedMessageId)
                .build();

        when(snsClient.publish(any(PublishRequest.class))).thenReturn(publishResponse);

        String messageId = snsService.sendSnsMessage(message, topicArn);

        assertEquals(expectedMessageId, messageId);
        verify(snsClient, times(1)).publish(
                PublishRequest.builder()
                        .topicArn(topicArn)
                        .message(message)
                        .build()
        );
    }
}
