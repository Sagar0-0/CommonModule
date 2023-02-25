package fit.asta.health.old.course.listing.viewmodel

import fit.asta.health.old.course.listing.data.CourseIndexData


interface CourseListingDataStore {
    fun updateList(list: List<CourseIndexData>)
    fun getCourse(position: Int): CourseIndexData
}