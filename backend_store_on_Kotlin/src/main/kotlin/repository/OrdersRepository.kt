package org.example.repository

import org.example.domain.orders
import org.example.domain.products
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

object OrdersDTO : Table("orders") {
    val id = varchar("id_orders", 50)
    val id_users = varchar("id_users", 50)
    val amount = integer("amount")

    override val primaryKey = PrimaryKey(id)
}

class OrdersRepository {
    init {
        Database.connect(
            url = "jdbc:postgresql://localhost:5432/store_db",
            driver = "org.postgresql.Driver",
            user = "store_user",
            password = "store123"
        )

        transaction {
            SchemaUtils.create(OrdersDTO)
        }
    }

    fun createOrders(id_users: String, amount: Int): Boolean {
        return transaction {
            OrdersDTO.insert {
                it[OrdersDTO.id_users] = id_users
                it[OrdersDTO.amount] = amount
            }
            true
        }
    }

    fun deleteOrders(id: String): Boolean {
        return transaction {
            val deletedCount = OrdersDTO.deleteWhere { OrdersDTO.id eq id }
            deletedCount > 0
        }
    }

    fun getAllOrders(): List<orders> {
        return transaction {
            OrdersDTO.selectAll().map {
                orders(it[OrdersDTO.id], it[OrdersDTO.id_users], it[OrdersDTO.amount])
            }
        }
    }

    fun getByIdOrders(id: String): orders {
        return transaction {
            OrdersDTO.select { ProductsDTO.id eq id }
                .map {
                    orders(
                        it[OrdersDTO.id],
                        it[OrdersDTO.id_users],
                        it[OrdersDTO.amount]
                    )
                }
        }.get(0)
    }
}