package dev.tpcoder.kafkaprojecttemplate.domain.example

data class KafkaPayload<T>(
    val id: String,
    val data: T
)
