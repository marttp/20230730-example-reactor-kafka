package dev.tpcoder.kafkaprojecttemplate.configuration

import dev.tpcoder.kafkaprojecttemplate.configuration.properties.KafkaChannelProperties
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.ByteArraySerializer
import org.apache.kafka.common.serialization.StringSerializer
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import reactor.kafka.sender.KafkaSender
import reactor.kafka.sender.SenderOptions
import java.util.*

@Configuration
class PublisherConfig(private val kafkaChannelProperties: KafkaChannelProperties) {

    private val logger = LoggerFactory.getLogger(PublisherConfig::class.java)

    private fun getSenderOptions(): SenderOptions<String, ByteArray> {
        val properties = Properties()
        properties[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = kafkaChannelProperties.bootstrapServers
        properties[ProducerConfig.CLIENT_ID_CONFIG] = kafkaChannelProperties.clientId
        properties[ProducerConfig.ACKS_CONFIG] = kafkaChannelProperties.acks
        properties[ProducerConfig.RETRIES_CONFIG] = kafkaChannelProperties.retries
        properties[ProducerConfig.LINGER_MS_CONFIG] = kafkaChannelProperties.lingerMs
        properties[ProducerConfig.MAX_REQUEST_SIZE_CONFIG] = kafkaChannelProperties.maxRequestSize
        properties[ProducerConfig.RECONNECT_BACKOFF_MS_CONFIG] = kafkaChannelProperties.reconnectBackoffMs
        properties[ProducerConfig.RETRY_BACKOFF_MS_CONFIG] = kafkaChannelProperties.retryBackoffMs
        properties[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        properties[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = ByteArraySerializer::class.java
        properties[ProducerConfig.COMPRESSION_TYPE_CONFIG] = kafkaChannelProperties.compressionType
        properties[ProducerConfig.BATCH_SIZE_CONFIG] = kafkaChannelProperties.batchSize
        properties[ProducerConfig.BUFFER_MEMORY_CONFIG] = kafkaChannelProperties.batchSize
        properties[ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG] = kafkaChannelProperties.requestTimeoutMs

        logger.info("Kafka publisher properties configured : {}", properties)

        return SenderOptions.create(properties)
    }

    @Bean
    fun kafkaSender(): KafkaSender<String, ByteArray> {
        return KafkaSender.create(getSenderOptions())
    }
}