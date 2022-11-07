package fit.asta.health.testimonials.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.firebase.model.AuthRepo
import fit.asta.health.testimonials.model.TestimonialRepo
import fit.asta.health.testimonials.model.network.NetTestimonial
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject


@ExperimentalCoroutinesApi
@HiltViewModel
class EditTestimonialViewModel
@Inject constructor(
    private val testimonialRepo: TestimonialRepo,
    private val authRepo: AuthRepo
) : ViewModel() {

    var netTestimonial by mutableStateOf<NetTestimonial?>(null)
        private set

    var title by mutableStateOf("")
        private set

    var testimonial by mutableStateOf("")
        private set

    private val mutableState = MutableStateFlow<TestimonialState>(TestimonialState.Loading)
    val state = mutableState.asStateFlow()

    init {

        authRepo.getUser()?.let {
            loadTestimonial(userId = it.uid)
        }
    }

    private fun loadTestimonial(userId: String) {
        viewModelScope.launch {
            testimonialRepo.getTestimonial(userId).catch { exception ->
                mutableState.value = TestimonialState.Error(exception)
            }.collect {
                this@EditTestimonialViewModel.netTestimonial = it.testimonial
            }
        }
    }

    private fun updateTestimonial(netTestimonial: NetTestimonial) {
        viewModelScope.launch {
            testimonialRepo.updateTestimonial(netTestimonial).catch { exception ->
                mutableState.value = TestimonialState.Error(exception)
            }.collect {
                mutableState.value = TestimonialState.Update(it)
            }
        }
    }

    fun onEvent(event: TestimonialEvent) {
        when (event) {
            is TestimonialEvent.OnTitleChange -> {
                title = event.title
            }
            is TestimonialEvent.OnTestimonialChange -> {
                testimonial = event.testimonial
            }
            is TestimonialEvent.OnSaveClick -> {
                updateTestimonial(
                    NetTestimonial(
                        apv = false,
                        id = netTestimonial?.id ?: "",
                        mda = listOf(),
                        rank = -1,
                        text = testimonial,
                        ttl = title,
                        uid = authRepo.getUser()?.uid ?: ""
                    )
                )
            }
        }
    }
}