package br.com.fiap.postech.service_pedido.service;

import br.com.fiap.postech.service_pedido.exception.PedidoNotFoundException;
import br.com.fiap.postech.service_pedido.records.PedidoRecord;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PedidoService {


    List<PedidoRecord> findAllPedidos() throws PedidoNotFoundException;

    void deletePedido(String id);

    PedidoRecord findPedidoById(String id) throws PedidoNotFoundException;

    PedidoRecord save(PedidoRecord pedido);
}
