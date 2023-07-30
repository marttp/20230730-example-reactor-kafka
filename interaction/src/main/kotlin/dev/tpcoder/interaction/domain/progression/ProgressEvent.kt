package dev.tpcoder.interaction.domain.progression

data class ProgressEvent(
    val courseId: Long,
    val userId: Long,
    val sectionId: Long,
    val status: String
)
