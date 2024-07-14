package br.com.fiap.postech.service_pedido.service;

import br.com.fiap.postech.service_pedido.entity.ProdutoItem;
import br.com.fiap.postech.service_pedido.records.PedidoCompletoRecord;
import br.com.fiap.postech.service_pedido.records.ClienteRecord;
import br.com.fiap.postech.service_pedido.records.ProdutoItemRecord;
import br.com.fiap.postech.service_pedido.records.ProdutoRecord;
import br.com.fiap.postech.service_pedido.service.impl.SnsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SnsServiceImplTest {

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
        // Arrange
        ClienteRecord cliente = new ClienteRecord(123L, "123.456.789-00", "cliente@example.com", "Cliente Teste");
        ProdutoRecord produtoItem1 = new ProdutoRecord (1L, "bicicleta","biciclieta eletrica",1200,2,"PENDENTE_EFETIVACAO");
        ProdutoRecord produtoItem2 = new ProdutoRecord(2L, "bicicleta","biciclieta eletrica",1200,2,"PENDENTE_EFETIVACAO");
        List<ProdutoRecord> produtos = List.of(produtoItem1, produtoItem2);
        PedidoCompletoRecord pedido = new PedidoCompletoRecord("pedidoId123", cliente, produtos, 50.0, "PROCESSING");

        String messageId = "12345";
        String topicArn = "arn:aws:sns:us-east-1:123456789012:MyTopic";
        System.setProperty("topic.arn.aws", topicArn);

        PublishResponse publishResponse = PublishResponse.builder()
                .messageId(messageId)
                .build();

        when(snsClient.publish(any(PublishRequest.class))).thenReturn(publishResponse);

        String result = snsService.sendSnsMessage(pedido);

        assertEquals(messageId, result);

        ArgumentCaptor<PublishRequest> publishRequestCaptor = ArgumentCaptor.forClass(PublishRequest.class);
        verify(snsClient).publish(publishRequestCaptor.capture());

        PublishRequest capturedRequest = publishRequestCaptor.getValue();
    }

    @Test
    void testConvertPedidoToJson() {
        ClienteRecord cliente = new ClienteRecord(123L, "123.456.789-00", "cliente@example.com", "Cliente Teste");
        ProdutoRecord produtoItem1 = new ProdutoRecord (1L, "bicicleta","biciclieta eletrica",1200,2,"PENDENTE_EFETIVACAO");
        ProdutoRecord produtoItem2 = new ProdutoRecord(2L, "bicicleta","biciclieta eletrica",1200,2,"PENDENTE_EFETIVACAO");
        List<ProdutoRecord> produtos = List.of(produtoItem1, produtoItem2);
        PedidoCompletoRecord pedido = new PedidoCompletoRecord("pedidoId123", cliente, produtos, 50.0, "PROCESSING");

        String jsonResult = snsService.convertPedidoToJson(pedido);

        String expectedJson = "{\"pedidoId\":\"pedidoId123\",\"clienteId\":{\"id\":123,\"cpf\":\"123.456.789-00\",\"email\":\"cliente@example.com\",\"nome\":\"Cliente Teste\"},\"produtosIds\":[{\"id\":1,\"nome\":\"bicicleta\",\"descricao\":\"biciclieta eletrica\",\"preco\":1200.0,\"quantidadeEstoque\":2,\"status\":\"PENDENTE_EFETIVACAO\"},{\"id\":2,\"nome\":\"bicicleta\",\"descricao\":\"biciclieta eletrica\",\"preco\":1200.0,\"quantidadeEstoque\":2,\"status\":\"PENDENTE_EFETIVACAO\"}],\"total\":50.0,\"status\":\"PROCESSING\"}";        assertEquals(expectedJson, jsonResult);
    }
}
