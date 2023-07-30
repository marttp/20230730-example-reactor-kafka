package dev.tpcoder.interaction.domain.progression

data class KafkaPayload<T>(
    val id: String,
    val data: T
)
