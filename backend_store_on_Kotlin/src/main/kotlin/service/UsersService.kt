package org.example.service

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.example.domain.users.CreateUsersRequest
import org.example.domain.users.LoginRequest
import org.example.repository.UserRepository
import org.example.service.authorization.GenerateToken

class UsersService {
    private val repository = UserRepository()
    private val token_User = GenerateToken()

    fun checkIsAdmin(call: ApplicationCall): Boolean {
        val authHeader = call.request.headers["Authorization"]
        val token = authHeader?.removePrefix("Bearer ") ?: return false

        val claims = token_User.verifyToken(token.toString()) ?: return false
        val role = claims["role"] as? String ?: return false

        return role == "admin"
    }


    suspend fun registerUser(call: ApplicationCall) {
        val req = call.receive<CreateUsersRequest>()
        val existingUser = repository.findUserByUsername(req.username)
        if (existingUser != null) {
            call.respond(HttpStatusCode.BadRequest, "Пользователь уже существует")
            return
        }

        val success = repository.createUser(req.username, req.passwordHash, req.role)
        if (success) {
            call.respond(HttpStatusCode.Created, "Пользователь зарегистрирован")
        } else {
            call.respond(HttpStatusCode.InternalServerError, "Ошибка при регистрации")
        }
    }

    suspend fun loginUser(call: ApplicationCall ) {
        val loginReq = call.receive<LoginRequest>()
        val user = repository.findUserByUsername(loginReq.username)
        if (user == null || !repository.verifyPassword(loginReq.password, user.passwordHash)) {
            call.respond(HttpStatusCode.Unauthorized, "Неверный логин или пароль")
            return
        }

        val token = token_User.Token_User(user)
        call.respond(mapOf("token" to token))
    }

}