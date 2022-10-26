package fit.asta.health.old_course.listing

import fit.asta.health.old_course.listing.data.CourseIndexData
import kotlinx.coroutines.flow.Flow

interface CourseListingRepo {
    suspend fun fetchCoursesList(
        categoryId: String,
        limit: Int,
        index: Int
    ): Flow<List<CourseIndexData>>
}