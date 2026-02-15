package org.example.repository

import org.example.domain.users.users
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.security.MessageDigest

object Users : Table("users") {
    val id = varchar("id_users", 50)
    val username = varchar("username", 50).uniqueIndex()
    val passwordHash = varchar("password_hash", 255)
    val role = varchar("role", 20).default("user")

    override val primaryKey = PrimaryKey(id)
}

class UserRepository {

    fun createUser(username: String, password: String, role: String = "user"): Boolean {
        val passwordHash = hashPassword(password)
        return transaction {
            Users.insert {
                it[this.username] = username
                it[this.passwordHash] = passwordHash
                it[this.role] = role
            }
            true
        }
    }

    fun findUserByUsername(username: String): users? {
        return transaction {
            Users.select { Users.username eq username }
                .map { users(it[Users.id], it[Users.username], it[Users.passwordHash], it[Users.role]) }
                .singleOrNull()
        }
    }

    fun verifyPassword(inputPassword: String, storedHash: String): Boolean {
        return hashPassword(inputPassword) == storedHash
    }

    private fun hashPassword(password: String): String {
        val md = MessageDigest.getInstance("SHA-256")
        return md.digest(password.toByteArray()).joinToString("") { "%02x".format(it) }
    }
}