package dev.tpcoder.kafkaprojecttemplate.configuration

import dev.tpcoder.kafkaprojecttemplate.configuration.properties.KafkaChannelProperties
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.ByteArrayDeserializer
import org.apache.kafka.common.serialization.StringDeserializer
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import reactor.kafka.receiver.KafkaReceiver
import reactor.kafka.receiver.ReceiverOptions
import java.time.Duration
import java.util.*

@Configuration
class ReceiverConfig(private val kafkaChannelProperties: KafkaChannelProperties) {

    private val logger = LoggerFactory.getLogger(ReceiverConfig::class.java)

    fun getReceiverOptions(): ReceiverOptions<String, ByteArray> {
        val properties = Properties()
        properties[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = kafkaChannelProperties.bootstrapServers
        properties[ConsumerConfig.GROUP_ID_CONFIG] = kafkaChannelProperties.groupId
        properties[ConsumerConfig.CLIENT_ID_CONFIG] = kafkaChannelProperties.clientId
        properties[ConsumerConfig.RETRY_BACKOFF_MS_CONFIG] = kafkaChannelProperties.retryBackoffMs
        properties[ConsumerConfig.AUTO_OFFSET_RESET_CONFIG] = kafkaChannelProperties.autoOffsetReset
        properties[ConsumerConfig.MAX_POLL_RECORDS_CONFIG] = kafkaChannelProperties.maxPollRecords
        properties[ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG] = kafkaChannelProperties.maxPollIntervalMs
        properties[ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG] = kafkaChannelProperties.sessionTimeoutMs
        properties[ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG] = kafkaChannelProperties.heartbeatIntervalMs
        properties[ConsumerConfig.REQUEST_TIMEOUT_MS_CONFIG] = kafkaChannelProperties.requestTimeoutMs
        properties[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        properties[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = ByteArrayDeserializer::class.java
        properties[ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG] = kafkaChannelProperties.autoCommitIntervalMs

        logger.info("Kafka receiver properties configured : {}", properties)

        return ReceiverOptions.create<String, ByteArray>(properties)
            .commitInterval(Duration.ofMillis(kafkaChannelProperties.commitIntervalMs))
            .commitBatchSize(kafkaChannelProperties.commitBatchSize)
    }

    @Bean
    fun interestTopicReceiver(): KafkaReceiver<String, ByteArray> {
        val receiverOption = getReceiverOptions()
        return KafkaReceiver.create(receiverOption.subscription(listOf(kafkaChannelProperties.topic)))
    }

}