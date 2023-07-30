package dev.tpcoder.contentdelivery.domain.course

data class KafkaPayload<T>(
    val id: String,
    val data: T
)
