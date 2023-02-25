package fit.asta.health.old.course.listing.viewmodel

import fit.asta.health.old.course.listing.data.CourseIndexData

sealed class CourseListingAction {

    class LoadCourses(val list: List<CourseIndexData>) : CourseListingAction()
    object Empty : CourseListingAction()
    class Error(val message: String) : CourseListingAction()

}