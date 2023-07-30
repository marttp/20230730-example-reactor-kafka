package dev.tpcoder.contentdelivery.domain.course.model

import dev.tpcoder.contentdelivery.domain.course.CourseStatusEnum

data class ProgressEvent(
    val courseId: Long,
    val userId: Long,
    val sectionId: Long,
    val status: CourseStatusEnum
)
