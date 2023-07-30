package dev.tpcoder.contentdelivery.configuration.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "communication-channel.kafka")
data class KafkaChannelProperties(
    val topic: String,
    val bootstrapServers: String,
    val clientId: String,

    val retryBackoffMs: Int = 1000,
    val requestTimeoutMs: Int = 30000,

    val compressionType: String,
    val acks: String = "all",
    val retries: Int = 2000,
    val maxRequestSize: Int = 1049600,
    val reconnectBackoffMs: Int = 1000,
    val batchSize: Int = 16384,
    val bufferMemory: Int = 33554432,
    val lingerMs: Int = 5,
)
