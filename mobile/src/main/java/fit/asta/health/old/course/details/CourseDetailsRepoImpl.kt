package fit.asta.health.old.course.details

import fit.asta.health.network.api.RemoteApis
import fit.asta.health.old.course.details.data.CourseDetailsData
import fit.asta.health.old.course.details.data.CourseDetailsDataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CourseDetailsRepoImpl(
    private val remoteApi: RemoteApis,
    private val dataMapper: CourseDetailsDataMapper
) : CourseDetailsRepo {

    override suspend fun fetchCourseDetails(courseId: String): Flow<CourseDetailsData> {
        return flow {
            emit(dataMapper.toMap(remoteApi.getCourseDetails(courseId).data))
        }
    }
}