package service;


import io.ktor.application.*
import io.ktor.http.*
import io.mockk.*
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import io.ktor.server.application.*
import org.example.repository.Orders_itemsRepository
import org.example.repository.OrdersRepository
import org.example.repository.ProductRepository
import org.example.repository.Audit_logsRepository
import org.example.service.Cache.OrderCache
import org.example.service.UsersService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.containers.KafkaContainer
import org.testcontainers.utility.DockerImageName

@Testcontainers
public class UsersService_test {

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

    private lateinit var usersService: UsersService

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
    fun test_checkIsAdmin_with_valid_admin_token() {
        val call = mockk<ApplicationCall>(relaxed = true)

        every { call.request.headers["Authorization"] } returns "Bearer validAdminToken"

        val claims = mapOf("role" to "admin")
        mockkObject(token_User)
        every { token_User.verifyToken("validAdminToken") } returns claims

        val result = checkIsAdmin(call)

        assertTrue(result)
    }

    @Test
    fun test_checkIsAdmin_with_admin_role() {
        val call = mockk<ApplicationCall>(relaxed = true)
        every { call.request.headers["Authorization"] } returns "Bearer someToken"

        val claims = mapOf("role" to "user")
        mockkObject(token_User)
        every { token_User.verifyToken("someToken") } returns claims

        val result = checkIsAdmin(call)
        assertFalse(result)
    }

    @Test
    fun test_checkIsAdmin_with_missing_Authorization_header() {
        val call = mockk<ApplicationCall>(relaxed = true)
        every { call.request.headers["Authorization"] } returns null

        val result = checkIsAdmin(call)
        assertFalse(result)
    }

    @Test
    fun test_checkIsAdmin_with_invalid_token() {
        val call = mockk<ApplicationCall>(relaxed = true)
        every { call.request.headers["Authorization"] } returns "Bearer invalidToken"

        mockkObject(token_User)
        every { token_User.verifyToken("invalidToken") } returns null

        val result = checkIsAdmin(call)
        assertFalse(result)
    }

    @AfterEach
    fun tearDown() {}

}
