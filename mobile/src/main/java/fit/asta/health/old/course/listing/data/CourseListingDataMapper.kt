package fit.asta.health.old.course.listing.data

import fit.asta.health.old.course.listing.networkdata.CoursesListNetData

class CourseListingDataMapper {

    fun toMap(coursesList: CoursesListNetData): List<CourseIndexData> {
        return coursesList.data.map {
            CourseIndexData().apply {
                uid = it.uid
                categoryId = it.cid
                title = it.ttl
                subTitle = it.dsc
                url = it.url
                audienceLevel = it.lvl
                intensity = it.int
                duration = it.dur
            }
        }
    }
}