package org.example.service

import org.example.domain.CreateProductsRequest
import org.example.domain.products
import org.example.repository.ProductRepository

class ProductsService {

    private val repository = ProductRepository()

    fun getAll(): List<products> {
        return repository.getAllProducts()
    }

    fun getById(id: String): products {
        return repository.getByIdProducts(id)
    }

    fun create(request: CreateProductsRequest): Boolean {
        return repository.createProduct(
            request.name, request.cost, request.stock
        )
    }

    fun delete(id: String): Boolean {
        return repository.deleteProduct(id)
    }
}
//private fun generateId(): String {
//        return java.util.UUID.randomUUID().toString()
//    }