package org.example.service

import java.util.concurrent.BlockingQueue
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread

data class Message(val id: String, val content: String)

class MessageWorker(
    private val queue: BlockingQueue<Message>,
    private val pollTimeout: Long = 1L // время ожидания при опросе
) {

    @Volatile
    private var running = false
    private lateinit var workerThread: Thread

    fun start() {
        if (running) return
        running = true
        workerThread = thread(start = true) {
            while (running) {
                try {
                    // Пытаемся взять сообщение с таймаутом
                    val message = queue.poll(pollTimeout, TimeUnit.SECONDS)
                    if (message != null) {
                        handleMessage(message)
                    }
                } catch (e: InterruptedException) {
                    println("Worker прерван")
                }
            }
            println("Worker остановлен")
        }
    }

    fun stop() {
        running = false
        workerThread.interrupt()
    }

    private fun handleMessage(message: Message) {
        logEvent("Обработка сообщения: ${message.id}")
        sendFakeEmail(
            to = "user@example.com",
            subject = "Новое сообщение ${message.id}",
            body = message.content
        )
    }

    private fun logEvent(event: String) {
        println("LOG: $event")
    }

    private fun sendFakeEmail(to: String, subject: String, body: String) {
        println("Отправка фейкового email на $to")
        println("Тема: $subject")
        println("Тело: $body")
    }
}