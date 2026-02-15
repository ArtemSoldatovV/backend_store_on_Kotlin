package org.example.repository

import org.example.domain.Orders_items
import org.example.domain.orders
import org.example.domain.products
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object Orders_itemsDTO : Table("orders_items") {
    val id = varchar("id_orders_items", 50)
    val id_orders = varchar("id_orders", 50)
    val id_products = varchar("id_products", 50)
    val quantity = integer("quantity")

    override val primaryKey = PrimaryKey(id)
}

class Orders_itemsRepository{
    init {
        Database.connect(
            url = "jdbc:postgresql://5432:5432/store_db",
            driver = "org.postgresql.Driver",
            user = "store_user",
            password = "store123"
        )

        transaction {
            SchemaUtils.create(Orders_itemsDTO)
        }
    }

    fun getAllOrders_items(): List<Orders_items> {
        return transaction {
            Orders_itemsDTO.selectAll().map {
                Orders_items(it[Orders_itemsDTO.id], it[Orders_itemsDTO.id_orders], it[Orders_itemsDTO.id_products],it[Orders_itemsDTO. quantity])
            }
        }
    }

    fun getByIdOrders_items(id: String): Orders_items {
        return transaction {
            Orders_itemsDTO.select { Orders_itemsDTO.id eq id }
                .map {
                    Orders_items(
                        it[Orders_itemsDTO.id],
                        it[Orders_itemsDTO.id_orders],
                        it[Orders_itemsDTO.id_products],
                        it[Orders_itemsDTO.quantity]
                    )
                }
        }.get(0) // .get(0) нужен для вывода одного единственного продукта
    }

    fun getById_Orders_items(id_orders: String): List<Orders_items>{
        return transaction {
            Orders_itemsDTO.select { Orders_itemsDTO.id_orders eq id_orders }
                .map {
                    Orders_items(
                        it[Orders_itemsDTO.id],
                        it[Orders_itemsDTO.id_orders],
                        it[Orders_itemsDTO.id_products],
                        it[Orders_itemsDTO.quantity]
                    )
                }
        }
    }


    fun getById_Orders(id_orders: String): List<products>{
        val Orders_items_list = getById_Orders_items(id_orders)
        val productRepository = ProductRepository()

        return Orders_items_list.mapNotNull { orderItem ->
            val id_products = orderItem.id_products
            productRepository.getByIdProducts(id_products)
        }

    }

}