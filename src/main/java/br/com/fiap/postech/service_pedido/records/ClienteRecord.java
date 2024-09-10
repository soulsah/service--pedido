package br.com.fiap.postech.service_pedido.records;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public record ClienteRecord (long id, @JsonProperty("CPF") String cpf, String email, String nome) implements Serializable {

}
