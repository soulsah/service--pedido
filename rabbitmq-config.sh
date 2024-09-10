#!/bin/bash

echo "Iniciando script de configuração do RabbitMQ..."

# Aguardar o RabbitMQ iniciar completamente
sleep 10

echo "Criando fila..."

# Conectar ao RabbitMQ e criar a fila
rabbitmqctl add_vhost /pedido_vhost
rabbitmqctl set_permissions -p /pedido_vhost user ".*" ".*" ".*"
rabbitmqctl set_policy -p /pedido_vhost my_policy "^pedido_queue$" '{"ha-mode":"all"}'

echo "Script de configuração concluído."
