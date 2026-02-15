package org.example.domain
//элементы заказа
data class Orders_items (
    val id_order_items: String,
    val id_orders: String,
    val id_products: String,
    val quantity: Int
)

data class CreateOrders_itemsRequest(
    val id_orders: String,
    val id_products: String,
    val quantity: Int
)