package org.example.service.Cache

import org.example.domain.CreateOrdersRequest
import org.example.domain.Orders_items
import org.example.domain.orders

class OrderCache {

    private val ordersMap = mutableMapOf<String, CreateOrdersRequest>()

    private val orderItemsMap = mutableMapOf<String, MutableList<Orders_items>>()

    fun cacheOrder(order: CreateOrdersRequest) {
        ordersMap[order.id_users] = order
        orderItemsMap.putIfAbsent(order.id_users, mutableListOf())
    }

    fun cacheOrderItem(orderItem: Orders_items) {
        val itemsList = orderItemsMap.getOrPut(orderItem.id_orders) { mutableListOf() }
        itemsList.add(orderItem)
    }

    fun getOrder(orderId: String): CreateOrdersRequest? = ordersMap[orderId]

    fun getOrderItems(orderId: String): List<Orders_items>? = orderItemsMap[orderId]

    fun clear() {
        ordersMap.clear()
        orderItemsMap.clear()
    }
}