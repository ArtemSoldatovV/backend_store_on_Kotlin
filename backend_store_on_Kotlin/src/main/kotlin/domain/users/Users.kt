package org.example.domain.users
import org.jetbrains.exposed.sql.Table

data class users (
    val id_users: String,
    val username: String,
    val passwordHash: String,
    val role: String
)

data class CreateUsersRequest(
    val username: String,
    val passwordHash: String,
    val role: String
)
data class LoginRequest(
    val username: String,
    val password: String
)