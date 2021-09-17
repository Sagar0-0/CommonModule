package fit.asta.health.course.listing.viewmodel

import fit.asta.health.course.listing.data.CourseIndexData

class CourseListingDataStoreImpl: CourseListingDataStore {
    private var courseIndexData: List<CourseIndexData> = listOf()

    override fun updateList(list: List<CourseIndexData>) {
        courseIndexData = list
    }

    override fun getCourse(position: Int): CourseIndexData {
        return courseIndexData[position]
    }
}