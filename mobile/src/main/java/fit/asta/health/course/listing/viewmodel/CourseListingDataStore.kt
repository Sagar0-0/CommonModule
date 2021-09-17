package fit.asta.health.course.listing.viewmodel

import fit.asta.health.course.listing.data.CourseIndexData


interface CourseListingDataStore {
    fun updateList(list: List<CourseIndexData>)
    fun getCourse(position: Int): CourseIndexData
}