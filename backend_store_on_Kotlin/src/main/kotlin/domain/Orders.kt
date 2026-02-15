package org.example.domain
//заказы
data class orders (
    val id_orders: String,
    val id_users: String,
    val amount : Int
)

data class CreateOrdersRequest(
    val id_users: String,
    val amount: Int,
    val products: List<Orders_items>
)
