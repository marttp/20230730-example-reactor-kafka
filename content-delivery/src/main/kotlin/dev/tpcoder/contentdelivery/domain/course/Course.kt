package dev.tpcoder.contentdelivery.domain.course

data class Course(
    val id: Long,
    val title: String,
    val description: String,
    val section: List<Section>
)
