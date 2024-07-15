package br.com.fiap.postech.service_pedido.service.impl;

import br.com.fiap.postech.service_pedido.entity.Pedido;
import br.com.fiap.postech.service_pedido.exception.PedidoNotFoundException;
import br.com.fiap.postech.service_pedido.mapper.PedidoMapper;
import br.com.fiap.postech.service_pedido.records.PedidoCompletoRecord;
import br.com.fiap.postech.service_pedido.records.PedidoRecord;
import br.com.fiap.postech.service_pedido.repository.PedidoRepository;
import br.com.fiap.postech.service_pedido.service.EnriquecerPedidoService;
import br.com.fiap.postech.service_pedido.service.PedidoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PedidoServiceImpl implements PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

  @Value("${url.pagamentos}")
  private String urlPagamentos;


    @Autowired
    private EnriquecerPedidoService enriquecerPedidoService;

    private  final String PENDENTE_EFETIVACAO = "PENDENTE_EFETIVACAO";


    @Override
    public List<PedidoRecord> findAllPedidos() throws PedidoNotFoundException {
        List<Pedido> pedidos = pedidoRepository.findAll();
        if(pedidos.isEmpty())
            throw new PedidoNotFoundException();
        return pedidos.stream().map(PedidoMapper::mapToRecord).collect(Collectors.toList());
    }

    @Override
    public void deletePedido(String id) {
        pedidoRepository.deleteById(id);
    }

    @Override
    public PedidoRecord findPedidoById(String id) throws PedidoNotFoundException {
        Optional<Pedido> pedidoOptional = pedidoRepository.findById(id);
        return pedidoOptional.map(PedidoMapper::mapToRecord).orElseThrow(PedidoNotFoundException::new);
    }

    @Override
    public PedidoRecord save(PedidoRecord pedidoRecord) {
        Pedido pedido = PedidoMapper.mapFromRecord(pedidoRecord);
        pedido.setPedidoId(UUID.randomUUID().toString());
        pedido.setTotal(enriquecerPedidoService.calcularValorProdutos(pedido));
        pedido.setStatus(PENDENTE_EFETIVACAO);
        Pedido savedPedido = pedidoRepository.save(pedido);
        chamarApiPagamentos(enriquecerPedidoService.enriquecerPedido(savedPedido));
        return PedidoMapper.mapToRecord(savedPedido);
    }

    private void chamarApiPagamentos(PedidoCompletoRecord pedidoRecord){
         RestTemplate restTemplate  = new RestTemplate();

         restTemplate.postForEntity(urlPagamentos+"/pagamentos", convertPedidoToJson(pedidoRecord), String.class);
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
