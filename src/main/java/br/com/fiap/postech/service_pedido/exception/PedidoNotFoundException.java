package br.com.fiap.postech.service_pedido.exception;

public class PedidoNotFoundException extends Exception{

    public PedidoNotFoundException(){
        super("Pedido nao encontrado");
    }

}
