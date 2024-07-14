package br.com.fiap.postech.service_pedido.service;

import br.com.fiap.postech.service_pedido.records.PedidoCompletoRecord;

public interface SnsService {

    public String sendSnsMessage(PedidoCompletoRecord pedido) ;
}
