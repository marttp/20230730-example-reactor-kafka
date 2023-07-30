package dev.tpcoder.contentdelivery

import org.springframework.boot.fromApplication
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.with

@TestConfiguration(proxyBeanMethods = false)
class TestApplication

fun main(args: Array<String>) {
	fromApplication<Application>().with(TestApplication::class).run(*args)
}
