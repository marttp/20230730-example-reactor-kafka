package dev.tpcoder.contentdelivery.domain.course.model

data class Course(
    val id: Long,
    val title: String,
    val description: String,
    val section: List<Section>
)
