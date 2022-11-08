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
import fit.asta.health.testimonials.model.network.User
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

    private val mutableState = MutableStateFlow<EditTestimonialState>(EditTestimonialState.Loading)
    val state = mutableState.asStateFlow()

    init {

        authRepo.getUser()?.let {
            loadTestimonial(userId = it.uid)
        }
    }

    private fun loadTestimonial(userId: String) {
        viewModelScope.launch {
            testimonialRepo.getTestimonial(userId).catch { exception ->
                mutableState.value = EditTestimonialState.Error(exception)
            }.collect {
                this@EditTestimonialViewModel.netTestimonial = it.testimonial
            }
        }
    }

    private fun updateTestimonial(netTestimonial: NetTestimonial) {
        viewModelScope.launch {
            testimonialRepo.updateTestimonial(netTestimonial).catch { exception ->
                mutableState.value = EditTestimonialState.Error(exception)
            }.collect {
                mutableState.value = EditTestimonialState.Update(it)
            }
        }
    }

    fun onEvent(event: EditTestimonialEvent) {
        when (event) {
            is EditTestimonialEvent.OnTitleChange -> {
                title = event.title
            }
            is EditTestimonialEvent.OnTestimonialChange -> {
                testimonial = event.testimonial
            }
            is EditTestimonialEvent.OnSaveClick -> {
                updateTestimonial(
                    NetTestimonial(
                        apv = false,
                        id = netTestimonial?.id ?: "",
                        media = listOf(),
                        rank = -1,
                        testimonial = testimonial,
                        title = title,
                        uid = authRepo.getUser()?.uid ?: "",
                        User("", "", "", "")
                    )
                )
            }
        }
    }
}