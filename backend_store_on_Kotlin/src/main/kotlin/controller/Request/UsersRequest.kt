package org.example.controller.Request

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.example.domain.users.CreateUsersRequest
import org.example.repository.UserRepository
import org.example.service.UsersService
import org.example.service.authorization.GenerateToken
import org.example.domain.users.LoginRequest

import org.koin.ktor.ext.inject

fun Route.UsersRequest() {
    val usersService: UsersService by inject()

    post("/auth/register") {
        usersService.registerUser(call)
    }

    post("/auth/login") {
        usersService.loginUser(call)
    }


}