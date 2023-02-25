package fit.asta.health.old.course.details

import fit.asta.health.old.course.details.data.CourseDetailsData
import kotlinx.coroutines.flow.Flow

interface CourseDetailsRepo {
    suspend fun fetchCourseDetails(courseId: String): Flow<CourseDetailsData>
}