package fit.asta.health.old.course.listing.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fit.asta.health.old.course.listing.CourseListingRepo
import fit.asta.health.old.course.listing.data.CourseIndexData
import kotlinx.coroutines.launch

class CourseListingViewModel(
    private val courseListingRepo: CourseListingRepo
) : ViewModel() {

    private val coursesLiveData = MutableLiveData<CourseListingAction>()
    private val dataStore = CourseListingDataStoreImpl()

    fun fetchCourses(categoryId: String, index: Int, limit: Int) {
        viewModelScope.launch {
            courseListingRepo.fetchCoursesList(
                categoryId, index, limit
            ).collect {
                updateCoursesList(it)
            }
        }
    }

    private fun updateCoursesList(list: List<CourseIndexData>) {
        dataStore.updateList(list)
        coursesLiveData.value = CourseListingAction.LoadCourses(list)
    }

    fun observeCourseListingLiveData(
        lifecycleOwner: LifecycleOwner,
        observer: CourseListingObserver
    ) {
        coursesLiveData.observe(lifecycleOwner, observer)
    }

    fun getCourseDetails(position: Int): CourseIndexData {
        return dataStore.getCourse(position)
    }
}