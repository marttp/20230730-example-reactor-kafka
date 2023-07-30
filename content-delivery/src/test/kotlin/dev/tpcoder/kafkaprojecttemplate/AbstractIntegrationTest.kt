package dev.tpcoder.kafkaprojecttemplate

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import

@SpringBootTest
@Import(value = [TestContainerConfig::class])
class AbstractIntegrationTest