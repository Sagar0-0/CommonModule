package fit.asta.health.testimonials.viewmodel

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fit.asta.health.auth.data.UserRepository
import fit.asta.health.testimonials.TestimonialsRepo
import fit.asta.health.testimonials.data.TestimonialData
import fit.asta.health.testimonials.ui.TestimonialsAction
import fit.asta.health.testimonials.ui.TestimonialsObserver
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class TestimonialsViewModel(
    private val testimonialsRepo: TestimonialsRepo,
    private val userRepo: UserRepository
) : ViewModel() {

    private val liveDataTestimonials = MutableLiveData<TestimonialsAction>()
    private val dataStore = TestimonialsDataStoreImpl()

    fun submitTestimonial(test: TestimonialData) {

        val testimonial = dataStore.updateMyTestimonial(test)
        viewModelScope.launch {

            if (testimonial.uid.isEmpty()) {
                testimonialsRepo.createTestimonial(testimonial)
                    .catch {
                        Log.i("POST ERROR", "Something wrong")
                    }.collect {

                    }
            } else {
                testimonialsRepo.updateTestimonial(testimonial)
                    .catch {
                        Log.i("PUT ERROR", "Something wrong")
                    }.collect {

                    }
            }

            liveDataTestimonials.value = TestimonialsAction.Empty
        }
    }

    fun fetchTestimonial() {
        viewModelScope.launch {
            userRepo.user()?.let { user ->
                testimonialsRepo.fetchTestimonial(user.uid).collect {
                    dataStore.createMyTestimonial(user.uid, it)
                    liveDataTestimonials.value = TestimonialsAction.LoadTestimonial(it)
                }
            }
        }
    }

    fun fetchTestimonials(limit: Int, index: Int) {

        viewModelScope.launch {
            testimonialsRepo.fetchTestimonialsList(limit, index)
                .collect {
                    updateTestimonials(it)
                }
        }
    }

    private fun updateTestimonials(list: List<TestimonialData>) {
        dataStore.updateTestimonialList(list)
        liveDataTestimonials.value = TestimonialsAction.LoadTestimonials(list)
    }

    fun observeTestimonialLiveData(lco: LifecycleOwner, observer: TestimonialsObserver) {
        liveDataTestimonials.observe(lco, observer)
    }
}