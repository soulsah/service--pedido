package br.com.fiap.postech.service_pedido.controller;

import br.com.fiap.postech.service_pedido.exception.PedidoNotFoundException;
import br.com.fiap.postech.service_pedido.producer.impl.PedidoProducerImpl;
import br.com.fiap.postech.service_pedido.records.ClienteRecord;
import br.com.fiap.postech.service_pedido.records.PedidoCompletoRecord;
import br.com.fiap.postech.service_pedido.records.PedidoRecord;
import br.com.fiap.postech.service_pedido.records.ProdutoRecord;
import br.com.fiap.postech.service_pedido.service.PedidoService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/pedido")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @DeleteMapping("/{id}")
    public void deletePedido(@PathVariable String id){
        pedidoService.deletePedido(id);
    }

    @GetMapping("")
    public ResponseEntity<List<PedidoRecord>> getAllPedidos() throws PedidoNotFoundException {

        return ResponseEntity.ok(pedidoService.findAllPedidos());
    }
    @GetMapping("/{id}")
    public ResponseEntity<PedidoRecord> getPedidosById(@PathVariable String id) throws PedidoNotFoundException {

        return ResponseEntity.ok(pedidoService.findPedidoById(id));
    }

    @PostMapping("")
    public ResponseEntity<PedidoRecord> createPedido(@RequestBody PedidoRecord pedido){
        return ResponseEntity.ok(pedidoService.save(pedido));
    }


}
