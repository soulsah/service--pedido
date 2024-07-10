package br.com.fiap.postech.service_pedido.service;

public interface SnsService {

    public String sendSnsMessage(String message,String topicArn) ;
}
