db = db.getSiblingDB('pedidos');

db.createCollection("pedidos", { capped: false });

var pedido1 = db.pedidos.insertOne({
    "pedidoId": "",
    "clienteId": "",
    "produtoId": [
        { "id": 122, "quantidade": 2 }
    ],
    "total": 2323.29,
    "status": "A efetuar"
}).insertedId;
