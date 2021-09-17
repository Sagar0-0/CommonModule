package fit.asta.health.course.listing.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fit.asta.health.course.listing.CourseListingRepo
import fit.asta.health.course.listing.data.CourseIndexData
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CourseListingViewModel(
    private val courseListingRepo: CourseListingRepo
) : ViewModel() {

    private val coursesLiveData = MutableLiveData<CourseListingAction>()
    private val dataStore = CourseListingDataStoreImpl()

    fun fetchCourses(categoryId: String, limit: Int, index: Int) {
        viewModelScope.launch {
            courseListingRepo.fetchCoursesList(
                categoryId, limit, index
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