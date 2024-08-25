db = db.getSiblingDB('pedidos');

db.createCollection("pedidos", { capped: false });

var pedido1 = db.pedidos.insertOne({
    "pedidoId": "08f756c4-d5ce-4ef8-90d4-76837f39c42c",
    "clienteId": "2",
    "produtoId": [
        { "id": 122, "quantidade": 2 }
    ],
    "total": 2323.29,
    "status": "A efetuar"
}).insertedId;
