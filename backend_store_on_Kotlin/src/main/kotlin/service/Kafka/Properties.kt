package org.example.service.Kafka

import java.util.Properties

val props = Properties().apply {
    put("bootstrap.servers", "localhost:9092") // адрес Kafka
    put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
}