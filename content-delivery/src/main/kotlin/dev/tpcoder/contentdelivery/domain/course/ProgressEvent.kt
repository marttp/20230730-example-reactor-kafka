package dev.tpcoder.contentdelivery.domain.course

data class ProgressEvent(
    val courseId: Long,
    val userId: Long,
    val sectionId: Long,
    val status: CourseStatusEnum
)
