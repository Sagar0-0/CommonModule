package fit.asta.health.old.course.details.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fit.asta.health.old.course.details.CourseDetailsRepo
import fit.asta.health.old.course.details.data.CourseDetailsData
import fit.asta.health.old.course.details.ui.CourseDetailsAction
import fit.asta.health.old.course.details.ui.CourseDetailsObserver
import kotlinx.coroutines.launch


class CourseDetailsViewModel(private val repo: CourseDetailsRepo) : ViewModel() {

    private val courseDetailsLiveData = MutableLiveData<CourseDetailsAction>()

    fun getCourseDetailsData(courseId: String) {
        viewModelScope.launch {
            repo.fetchCourseDetails(courseId)
                .collect {
                    updateCourseDetails(it)
                }
        }
    }

    private fun updateCourseDetails(courseDetails: CourseDetailsData) {
        courseDetailsLiveData.value = CourseDetailsAction.LoadCourseDetails(courseDetails)
    }

    fun observeCourseDetailsLiveData(
        lifecycleOwner: LifecycleOwner,
        observer: CourseDetailsObserver
    ) {
        courseDetailsLiveData.observe(lifecycleOwner, observer)
    }
}