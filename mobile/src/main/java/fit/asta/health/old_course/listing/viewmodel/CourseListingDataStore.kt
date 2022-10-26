package fit.asta.health.old_course.listing.viewmodel

import fit.asta.health.old_course.listing.data.CourseIndexData


interface CourseListingDataStore {
    fun updateList(list: List<CourseIndexData>)
    fun getCourse(position: Int): CourseIndexData
}