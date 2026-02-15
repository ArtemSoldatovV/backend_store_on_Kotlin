package org.example.service.Kafka

import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord

val producer = KafkaProducer<String, String>(props)

fun sendMessage(topic: String, message: String) {
    val record = ProducerRecord<String, String>(topic, message)
    producer.send(record) { metadata, exception ->
        if (exception != null) {
            println("Ошибка при отправке: ${exception.message}")
        } else {
            println("Сообщение отправлено в тему ${metadata.topic()}, партицию ${metadata.partition()}, смещение ${metadata.offset()}")
        }
    }
}
