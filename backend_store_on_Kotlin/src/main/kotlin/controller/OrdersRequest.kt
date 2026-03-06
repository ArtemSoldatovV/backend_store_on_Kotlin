package controller

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.example.domain.CreateOrdersRequest
import org.example.service.OrdersService
import org.example.service.UsersService

import org.koin.ktor.ext.inject

fun Route.OrdersRequest() {
    val ordersService: OrdersService by inject()
    val usersService: UsersService by inject()

    get("/orders") {
        call.respond(ordersService.getAll())
    }

    post("/orders") {
        val req = call.receive<CreateOrdersRequest>()
        val orders = ordersService.create(req)
        call.respond(orders)
    }

    delete("/orders/{id}"){
        val id = call.parameters["id"]
        if (id.isNullOrBlank()) {
            call.respondText("Missing or empty id", status = io.ktor.http.HttpStatusCode.BadRequest)
            return@delete
        }
        call.respond(ordersService.getById (id) )
    }
    get("/stats/orders") {
        if (usersService.checkIsAdmin(call)) {
            call.respond(ordersService.getAll_plus())
        }else{
            call.respond("you don't have access")
        }

    }

}