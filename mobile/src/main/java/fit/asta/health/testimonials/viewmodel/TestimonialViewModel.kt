package fit.asta.health.testimonials.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.testimonials.intent.TestimonialState
import fit.asta.health.testimonials.model.TestimonialRepo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject


@ExperimentalCoroutinesApi
@HiltViewModel
class TestimonialViewModel
@Inject constructor(
    private val testimonialRepo: TestimonialRepo,
) : ViewModel() {

    private val mutableState = MutableStateFlow<TestimonialState>(TestimonialState.Loading)
    val state = mutableState.asStateFlow()

    init {
        loadWaterToolData(10, 0)
    }

    private fun loadWaterToolData(limit: Int, index: Int) {
        viewModelScope.launch {
            testimonialRepo.getTestimonials(limit = limit, index = index).catch { exception ->
                mutableState.value = TestimonialState.Error(exception)
            }.collect {
                mutableState.value = TestimonialState.Success(it)
            }
        }
    }
}