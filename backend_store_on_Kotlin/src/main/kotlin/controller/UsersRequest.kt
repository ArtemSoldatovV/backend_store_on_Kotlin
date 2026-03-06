package controller

import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.example.service.UsersService

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