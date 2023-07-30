package dev.tpcoder.kafkaprojecttemplate

import org.springframework.boot.fromApplication
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.with

@TestConfiguration(proxyBeanMethods = false)
class TestKafkaProjectTemplateApplication

fun main(args: Array<String>) {
	fromApplication<KafkaProjectTemplateApplication>().with(TestKafkaProjectTemplateApplication::class).run(*args)
}
