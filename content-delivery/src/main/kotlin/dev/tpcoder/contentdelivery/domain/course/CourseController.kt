package dev.tpcoder.contentdelivery.domain.course

import com.fasterxml.jackson.databind.ObjectMapper
import dev.tpcoder.contentdelivery.configuration.properties.KafkaChannelProperties
import org.apache.kafka.clients.producer.ProducerRecord
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import reactor.kafka.sender.KafkaSender
import reactor.kafka.sender.SenderRecord
import java.time.Instant
import java.util.*

@RestController
@RequestMapping("/courses")
class CourseController(
    private val objectMapper: ObjectMapper,
    private val kafkaSender: KafkaSender<String, ByteArray>,
    private val kafkaChannelProperties: KafkaChannelProperties
) {

    private val logger = LoggerFactory.getLogger(CourseController::class.java)

    @GetMapping
    fun getAllCourses(): List<Course> {
        return listOf(
            Course(
                id = 1,
                title = "Backend development in Java",
                description = "Backend development for people who want to be Java developers",
                section = listOf(
                    Section(
                        id = 1,
                        title = "Introduction to Java"
                    ),
                    Section(
                        id = 2,
                        title = "Spring Boot"
                    )
                )
            )
        )
    }

    @PostMapping("/progress")
    fun send(@RequestBody body: ProgressEvent): Mono<Void> {
        val id = UUID.randomUUID()
        val key = id.toString()
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
            key, // Kafka Message key for idempotency
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