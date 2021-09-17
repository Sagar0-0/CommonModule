package fit.asta.health.course.listing

import fit.asta.health.course.listing.data.CourseIndexData
import fit.asta.health.course.listing.data.CourseListingDataMapper
import fit.asta.health.network.api.RemoteApis
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CourseListingRepoImpl(
    private val remoteApi: RemoteApis,
    private val courseListDataMapper: CourseListingDataMapper
) : CourseListingRepo {

    override suspend fun fetchCoursesList(
        categoryId: String,
        limit: Int,
        index: Int
    ): Flow<List<CourseIndexData>> {
        return flow {
            emit(courseListDataMapper.toMap(remoteApi.getCoursesList(categoryId, limit, index)))
        }
    }
}