package org.example.repository

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import org.example.domain.products

object ProductsDTO : Table("products") {
    val id = varchar("id_products", 50)
    val name = varchar("name", 255)
    val cost = integer("cost")
    val stock = integer("stock")

    override val primaryKey = PrimaryKey(id)
}

class ProductRepository {

    init {
        Database.connect(
            url = "jdbc:postgresql://localhost:5432/store_db",
            driver = "org.postgresql.Driver",
            user = "store_user",
            password = "store123"
        )

        transaction {
            SchemaUtils.create(ProductsDTO)
        }
    }

    fun createProduct(name: String, cost: Int, stock: Int): Boolean {
        return transaction {
            ProductsDTO.insert {
                it[ProductsDTO.name] = name
                it[ProductsDTO.cost] = cost
                it[ProductsDTO.stock] = stock
            }
            true
        }
    }
    fun updateProduct(id: String, name: String, cost: Int, stock: Int) {
        return transaction {
            val updatedRows = ProductsDTO.update({ ProductsDTO.id eq id }) {
                it[ProductsDTO.name] = name
                it[ProductsDTO.cost] = cost
                it[ProductsDTO.stock] = stock
            }

        }
    }

    fun deleteProduct(id: String): Boolean {
        return transaction {
            val deletedCount = ProductsDTO.deleteWhere { ProductsDTO.id eq id }
            deletedCount > 0
        }
    }

    fun getAllProducts(): List<products> {
        return transaction {
            ProductsDTO.selectAll().map {
                products(it[ProductsDTO.id], it[ProductsDTO.name], it[ProductsDTO.cost], it[ProductsDTO.stock])
            }
        }
    }

    fun getByIdProducts(id: String): products {
        return transaction {
            ProductsDTO.select { ProductsDTO.id eq id }
                .map {
                    products(
                        it[ProductsDTO.id],
                        it[ProductsDTO.name],
                        it[ProductsDTO.cost],
                        it[ProductsDTO.stock]
                    )
                }
        }.get(0) // .get(0) нужен для вывода одного единственного продукта
    }

}
