package service

import org.example.domain.CreateProductsRequest
import org.example.repository.Orders_itemsRepository
import org.example.repository.OrdersRepository
import org.example.repository.ProductRepository
import org.example.repository.Audit_logsRepository
import org.example.service.Cache.OrderCache
import org.example.service.ProductsService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.containers.KafkaContainer
import org.testcontainers.utility.DockerImageName

@Testcontainers
class ProductsService_test {

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

    private lateinit var productsService: ProductsService

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
        val createRequest = CreateProductsRequest(
            name = "1", cost = 1, stock = 1
        )


        val result = productsService.create(createRequest)
        assert(result) { "Успех" }

    }

    @AfterEach
    fun tearDown() {}

}