package fit.asta.health.course.details

import fit.asta.health.course.details.data.CourseDetailsData
import kotlinx.coroutines.flow.Flow

interface CourseDetailsRepo {
    suspend fun fetchCourseDetails(courseId: String): Flow<CourseDetailsData>
}