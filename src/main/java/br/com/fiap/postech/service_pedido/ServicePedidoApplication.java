package br.com.fiap.postech.service_pedido;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableRabbit
@SpringBootApplication
public class ServicePedidoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServicePedidoApplication.class, args);
	}

}
