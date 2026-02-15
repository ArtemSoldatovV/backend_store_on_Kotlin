package org.example.service

import org.example.domain.CreateOrdersRequest
import org.example.domain.Orders_items
import org.example.domain.orders
import org.example.repository.OrdersRepository
import org.example.repository.Audit_logsRepository
import org.example.repository.Orders_itemsRepository
import org.example.repository.ProductRepository
import org.example.service.Cache.OrderCache
import org.example.service.Kafka.sendMessage

class OrdersService {
    private val repository = OrdersRepository()
    private val productRepository = ProductRepository()
    private val audit_logsRepository = Audit_logsRepository()
    private val orders_itemsRepository = Orders_itemsRepository()
    private val orderCache = OrderCache()

    fun getAll(): List<orders> {
        return repository.getAllOrders()
    }

    fun getAll_plus(): List<Orders_items> {
        return orders_itemsRepository.getAllOrders_items();
    }

    fun getById(id: String): orders {
        return repository.getByIdOrders(id)
    }

    fun create(request: CreateOrdersRequest): Boolean {


//        При создании заказа:
//        проверять наличие товара
        for (i in request.products ){
            if (productRepository.getByIdProducts(i.id_products).stock - i.quantity < 0){//логика такая если число книг в библиотеке - желаемое заказать менше 0 то значит их не хвати на заказ, значит отказ

                //отказ
                return false
            }
        }

//        уменьшать stock
        for (i in request.products ) {
            productRepository.updateProduct(
                i.id_products,
                productRepository.getByIdProducts(i.id_products).name,
                productRepository.getByIdProducts(i.id_products).cost,
                productRepository.getByIdProducts(i.id_products).stock - i.quantity
            )
        }

//        отправлять сообщение в очередь (Kafka)
        sendMessage(
            "products",
            "The order has been placed"
        )
//        кэшировать данные заказа
        orderCache.cacheOrder(request)

//        писать запись в audit logs
        audit_logsRepository.createAudit_logs(request.id_users, "create",request.javaClass.toString(),"ok"  )
        return repository.createOrders(
            request.id_users, request.amount
        )
    }

}
//orders_itemsRepository.getById_ordersOrders(request.id_orders)