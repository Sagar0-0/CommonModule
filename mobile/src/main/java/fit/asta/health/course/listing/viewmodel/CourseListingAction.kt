package fit.asta.health.course.listing.viewmodel

import fit.asta.health.course.listing.data.CourseIndexData

sealed class CourseListingAction {

    class LoadCourses(val list: List<CourseIndexData>) : CourseListingAction()
    object Empty : CourseListingAction()
    class Error(val message: String) : CourseListingAction()

}