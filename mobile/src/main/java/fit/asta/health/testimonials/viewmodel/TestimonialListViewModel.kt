package fit.asta.health.testimonials.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.testimonials.model.TestimonialRepo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject


@ExperimentalCoroutinesApi
@HiltViewModel
class TestimonialListViewModel
@Inject constructor(
    private val testimonialRepo: TestimonialRepo,
) : ViewModel() {

    private val mutableState = MutableStateFlow<TestimonialListState>(TestimonialListState.Loading)
    val state = mutableState.asStateFlow()

    init {
        loadTestimonials(10, 0)
    }

    private fun loadTestimonials(limit: Int, index: Int) {
        viewModelScope.launch {
            testimonialRepo.getTestimonials(limit = limit, index = index).catch { exception ->
                mutableState.value = TestimonialListState.Error(exception)
            }.collect {
                mutableState.value = TestimonialListState.Success(it.testimonials)
            }
        }
    }

    fun onEvent(event: TestimonialListEvent) {
        when (event) {
            is TestimonialListEvent.OnNextPage -> loadTestimonials(
                limit = event.limit,
                index = event.index
            )
        }
    }
}