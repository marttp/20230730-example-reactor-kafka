package dev.tpcoder.interaction.domain.progression

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import reactor.core.Disposable
import reactor.core.Disposables
import reactor.core.publisher.Mono
import reactor.kafka.receiver.KafkaReceiver

@Service
class CourseProgressReceiverWorker(
    private val objectMapper: ObjectMapper,
    @Qualifier("interestTopicReceiver") private val kafkaReceiver: KafkaReceiver<String, ByteArray>
) {

    private val logger = LoggerFactory.getLogger(CourseProgressReceiverWorker::class.java)
    private val disposables = Disposables.composite()

    @PostConstruct
    fun connect() {
        disposables.add(
            receiver()
        )
    }

    @PreDestroy
    fun disconnect() {
        this.disposables.dispose()
    }

    fun receiver(): Disposable {
        return kafkaReceiver.receive()
            // .delayElements(Duration.ofSeconds(20))
            .doOnNext { logger.info("Key : ${it.key()}") }
            // Don't subscribe on this step since it will load all the message to workload at once
            // Take advantage of Backpressure
            .flatMap {
                val payload = objectMapper.readValue(
                    it.value(),
                    KafkaPayload::class.java
                )
                logger.info("Received payload : $payload")
                Mono.just(it)
            }
            .doOnNext {
                // Acknowledge the message. The offset will be committed automatically based on interval
                it.receiverOffset().acknowledge()
            }
            // Subscribe this step as the last step
            .subscribe()
    }

}