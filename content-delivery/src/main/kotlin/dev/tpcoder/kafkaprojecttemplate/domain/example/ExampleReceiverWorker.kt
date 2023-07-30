package dev.tpcoder.kafkaprojecttemplate.domain.example

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.core.ParameterizedTypeReference
import org.springframework.stereotype.Service
import reactor.core.Disposable
import reactor.core.Disposables
import reactor.kafka.receiver.KafkaReceiver

@Service
class ExampleReceiverWorker(
    private val objectMapper: ObjectMapper,
    @Qualifier("interestTopicReceiver") private val kafkaReceiver: KafkaReceiver<String, ByteArray>
) {

    private val logger = LoggerFactory.getLogger(ExampleReceiverWorker::class.java)
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
            .doOnNext { logger.info("Key : ${it.key()}") }
            .subscribe {
                val payload = objectMapper.readValue(
                    it.value(),
                    KafkaPayload::class.java
                )
                logger.info("Received payload : $payload")
                it.receiverOffset().acknowledge()
            }
    }

}