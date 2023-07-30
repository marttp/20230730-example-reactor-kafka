package dev.tpcoder.kafkaprojecttemplate.domain.example

import com.fasterxml.jackson.databind.ObjectMapper
import dev.tpcoder.kafkaprojecttemplate.configuration.properties.KafkaChannelProperties
import org.apache.kafka.clients.producer.ProducerRecord
import org.slf4j.LoggerFactory
import org.springframework.util.DigestUtils
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.Disposable
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import reactor.kafka.sender.KafkaSender
import reactor.kafka.sender.SenderRecord
import java.time.Instant
import java.util.*

@RestController
@RequestMapping("/examples")
class ExampleController(
    private val objectMapper: ObjectMapper,
    private val kafkaSender: KafkaSender<String, ByteArray>,
    private val kafkaChannelProperties: KafkaChannelProperties
) {

    private val logger = LoggerFactory.getLogger(ExampleController::class.java)

    @PostMapping
    fun send(@RequestBody body: ExampleMessage): Mono<Void> {
        val id = UUID.randomUUID()
        val key = DigestUtils.md5DigestAsHex(id.toString().toByteArray())
        val payload = objectMapper.writeValueAsBytes(
            KafkaPayload(
                id = id.toString(),
                data = body
            )
        )
        val producerRecord = ProducerRecord(
            kafkaChannelProperties.topic,
            null,
            Instant.now().toEpochMilli(),
            key,
            payload,
            null
        )
        val senderRecord = SenderRecord.create(producerRecord, id)
        return kafkaSender.send(Mono.just(senderRecord))
            .doOnNext { senderResult ->
                logger.info("Message sent to kafka channel: $senderResult")
            }
            .then()
    }
}