package dev.tpcoder.kafkaprojecttemplate

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.test.context.DynamicPropertyRegistry
import org.testcontainers.containers.KafkaContainer
import org.testcontainers.utility.DockerImageName

@TestConfiguration(proxyBeanMethods = false)
class TestContainerConfig {

    @Bean
    fun kafkaContainer(registry: DynamicPropertyRegistry): KafkaContainer {
        val kafkaContainer = KafkaContainer(
            DockerImageName.parse("confluentinc/cp-kafka:7.2.1")
        )
        // communication-channel.kafka.bootstrap-servers=localhost:29092
        registry.add("communication-channel.kafka.bootstrap-servers") {
            kafkaContainer.bootstrapServers
        }
        return kafkaContainer
    }
}