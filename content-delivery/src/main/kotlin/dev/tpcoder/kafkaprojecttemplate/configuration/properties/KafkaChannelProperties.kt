package dev.tpcoder.kafkaprojecttemplate.configuration.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "communication-channel.kafka")
data class KafkaChannelProperties(
    val topic: String,
    val bootstrapServers: String,
    val clientId: String,
    val groupId: String,
    val retryBackoffMs: Int = 1000,
    val requestTimeoutMs: Int = 30000,
    val autoOffsetReset: String = "earliest",
    val compressionType: String,
    val acks: String = "all",
    val retries: Int = 2000,
    val maxRequestSize: Int = 1049600,
    val reconnectBackoffMs: Int = 1000,
    val batchSize: Int = 16384,
    val bufferMemory: Int = 33554432,
    val lingerMs: Int = 5,

    val maxPollRecords: Int = 250,
    val maxPollIntervalMs: Int = 300_000,
    val sessionTimeoutMs: Int = 10_000,
    val heartbeatIntervalMs: Int = 3000,
    val autoCommitIntervalMs: Int = 5000,
    val commitBatchSize: Int = 0,
    val commitIntervalMs: Long = 5000L
)
