package org.example.controller.Request

import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import org.koin.ktor.ext.inject
import io.ktor.server.application.*
import org.example.domain.CreateProductsRequest
import org.example.service.ProductsService
import org.example.service.UsersService

fun Route.ProductsRoutes() {
    val productsService: ProductsService by inject()
    val usersService: UsersService by inject()

    get("/products") {
        call.respond(productsService.getAll())
    }
    get("/products/{id}") {
        val id = call.parameters["id"]
        if (id.isNullOrBlank()) {
            call.respondText("Missing or empty id", status = io.ktor.http.HttpStatusCode.BadRequest)
            return@get
        }
        call.respond(productsService.getById(id) )
    }


    post("/products") {
        if (usersService.checkIsAdmin(call)) {

            call.respond("Вы — админ!")
            val req = call.receive<CreateProductsRequest>()
            val product = productsService.create(req)
            call.respond(product)
        }else{
            call.respond("you don't have access")
        }

    }

    put("/products/{id}") {
        if (usersService.checkIsAdmin(call)) {


            val id = call.parameters["id"]
            if (id.isNullOrBlank()) {
                call.respondText("Missing or empty id", status = io.ktor.http.HttpStatusCode.BadRequest)
                return@put
            }

            val product = productsService.getById(id)
            if (product != null) {
                call.respond(product)
            } else {
                call.respondText("Product not found", status = io.ktor.http.HttpStatusCode.NotFound)
            }
        }else{
            call.respond("you don't have access")
        }


    }

    delete("/products/{id}") {
        if (usersService.checkIsAdmin(call)) {


            val id = call.parameters["id"]
            if (id == null || id.isBlank()) {
                call.respondText("Missing or empty id", status = io.ktor.http.HttpStatusCode.BadRequest)
                return@delete
            }

            val deleted = productsService.delete(id)
            if (deleted) {
                call.respondText("Product deleted successfully", status = io.ktor.http.HttpStatusCode.OK)
            } else {
                call.respondText("Product not found", status = io.ktor.http.HttpStatusCode.NotFound)
            }
        }else{
            call.respond("you don't have access")
        }


    }
}