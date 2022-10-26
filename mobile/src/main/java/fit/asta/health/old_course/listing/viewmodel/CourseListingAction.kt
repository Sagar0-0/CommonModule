package fit.asta.health.old_course.listing.viewmodel

import fit.asta.health.old_course.listing.data.CourseIndexData

sealed class CourseListingAction {

    class LoadCourses(val list: List<CourseIndexData>) : CourseListingAction()
    object Empty : CourseListingAction()
    class Error(val message: String) : CourseListingAction()

}