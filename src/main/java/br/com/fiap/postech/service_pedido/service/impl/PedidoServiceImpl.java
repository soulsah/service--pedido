package br.com.fiap.postech.service_pedido.service.impl;

import br.com.fiap.postech.service_pedido.entity.Pedido;
import br.com.fiap.postech.service_pedido.exception.PedidoNotFoundException;
import br.com.fiap.postech.service_pedido.mapper.PedidoMapper;
import br.com.fiap.postech.service_pedido.records.PedidoRecord;
import br.com.fiap.postech.service_pedido.repository.PedidoRepository;
import br.com.fiap.postech.service_pedido.service.EnriquecerPedidoService;
import br.com.fiap.postech.service_pedido.service.PedidoService;
import br.com.fiap.postech.service_pedido.service.SnsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PedidoServiceImpl implements PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;


    @Autowired
    private EnriquecerPedidoService enriquecerPedidoService;

    @Autowired
    private SnsService snsService;


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
        pedido.setTotal(enriquecerPedidoService.calcularValorProdutos(pedido));
        pedido.setStatus("PENDENTE_EFETIVACAO");
        Pedido savedPedido = pedidoRepository.save(pedido);
        snsService.sendSnsMessage(enriquecerPedidoService.EnriquecerPedido(savedPedido));
        return PedidoMapper.mapToRecord(savedPedido);
    }
}
