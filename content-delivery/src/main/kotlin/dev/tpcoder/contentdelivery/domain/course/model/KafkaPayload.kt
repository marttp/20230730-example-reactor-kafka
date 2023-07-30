package dev.tpcoder.contentdelivery.domain.course.model

data class KafkaPayload<T>(
    val id: String,
    val data: T
)
