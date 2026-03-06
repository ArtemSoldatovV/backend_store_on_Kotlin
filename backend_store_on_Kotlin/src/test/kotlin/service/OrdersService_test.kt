package service

import org.example.domain.CreateOrdersRequest
import org.example.domain.Orders_items
import org.example.repository.Orders_itemsRepository
import org.example.repository.OrdersRepository
import org.example.repository.ProductRepository
import org.example.repository.Audit_logsRepository
import org.example.service.Cache.OrderCache
import org.example.service.OrdersService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.containers.KafkaContainer
import org.testcontainers.utility.DockerImageName

@Testcontainers
class OrdersService_test {

    companion object {
        @JvmStatic
        val postgres = PostgreSQLContainer<Nothing>("postgres:13").apply {
            withDatabaseName("testdb")
            withUsername("user")
            withPassword("password")
            start()
        }

        @JvmStatic
        val kafka = KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.4.0")).apply {
            start()
        }
    }

    private lateinit var ordersService: OrdersService

    @BeforeEach
    fun setUp() {
        val dbUrl = postgres.jdbcUrl
        val dbUser = postgres.username
        val dbPass = postgres.password

        val ordersRepository = OrdersRepository()
        val productRepository = ProductRepository()
        val auditLogsRepository = Audit_logsRepository()
        val ordersItemsRepository = Orders_itemsRepository()

        val orderCache = OrderCache()
    }

    @Test
    fun test_create() {
        val createRequest = CreateOrdersRequest(
            id_users = "user123",
            amount = 100,
            products = listOf(
                Orders_items(id_order_items = "1", id_orders = "1", id_products = "1", quantity = 20),
                Orders_items(id_order_items = "2", id_orders = "1", id_products = "2", quantity = 20)
            )
        )


        val result = ordersService.create(createRequest)
        assert(result) { "Заказ должен быть успешно создан" }

    }

    @AfterEach
    fun tearDown() {}

}