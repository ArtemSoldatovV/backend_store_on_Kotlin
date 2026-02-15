package org.example.domain
//продукты
data class products (
    val id_products: String,
    val name: String,
    val cost: Int,
    val stock: Int
)

data class CreateProductsRequest(
    val name: String,
    val cost: Int,
    val stock: Int
)