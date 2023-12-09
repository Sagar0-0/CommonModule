package fit.asta.health.feature.testimonials.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.auth.repo.AuthRepo
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.toUiState
import fit.asta.health.data.testimonials.model.SaveTestimonialResponse
import fit.asta.health.data.testimonials.model.Testimonial
import fit.asta.health.data.testimonials.repo.TestimonialRepo
import fit.asta.health.feature.testimonials.events.MediaType
import fit.asta.health.feature.testimonials.events.TestimonialEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TestimonialViewModel @Inject constructor(
    private val testimonialRepo: TestimonialRepo,
    private val authRepo: AuthRepo,
) : ViewModel() {


    /**
     * This variable fetches and stores the user's Testimonial Details from the server and shows the
     * state of the Api call
     */
    private val _userTestimonialApiState = MutableStateFlow<UiState<Testimonial>>(UiState.Idle)
    val userTestimonialApiState = _userTestimonialApiState.asStateFlow()

    /**
     * This variable contains the testimonial data for the UI changes and so
     */
    val testimonialData: MutableStateFlow<Testimonial> = MutableStateFlow(Testimonial())

    /**
     * This function fetches the user testimonial from the server
     */
    private fun getUserTestimonial() {
        viewModelScope.launch {

            // Fetching the user's Uid from the firebase to refer from the backend Server
            authRepo.getUser()?.let { user ->

                // Fetching the testimonial from the server
                _userTestimonialApiState.value = testimonialRepo
                    .getUserTestimonial(user.uid)
                    .toUiState()

                if (_userTestimonialApiState.value is UiState.Success)
                    testimonialData.value =
                        (_userTestimonialApiState.value as UiState.Success<Testimonial>).data
            }
        }
    }

    private val _testimonialSubmitApiState =
        MutableStateFlow<UiState<SaveTestimonialResponse>>(UiState.Loading)
    val testimonialSubmitApiState = _testimonialSubmitApiState.asStateFlow()

    private fun postUserTestimonial() {
        viewModelScope.launch {
            authRepo.getUser()?.let {

                testimonialData.value = testimonialData.value.copy(
                    user = testimonialData.value.user.copy(
                        name = it.name!!,
                        url = it.photoUrl.toString()
                    ),
                    userId = it.uid
                )

                _testimonialSubmitApiState.value = testimonialRepo
                    .saveTestimonial(testimonialData.value)
                    .toUiState()
            }
        }
    }


    fun onEvent(event: TestimonialEvent) {
        when (event) {
            is TestimonialEvent.GetUserTestimonial -> {
                getUserTestimonial()
            }

            is TestimonialEvent.OnTypeChange -> {
                testimonialData.value = testimonialData.value.copy(type = event.type.value)
            }

            is TestimonialEvent.OnTitleChange -> {
                testimonialData.value = testimonialData.value.copy(title = event.title)
            }

            is TestimonialEvent.OnTestimonialChange -> {
                testimonialData.value = testimonialData.value.copy(testimonial = event.testimonial)
            }

            is TestimonialEvent.OnRoleChange -> {
                val user = testimonialData.value.user.copy(role = event.role)
                testimonialData.value = testimonialData.value.copy(user = user)
            }

            is TestimonialEvent.OnOrgChange -> {
                val user = testimonialData.value.user.copy(org = event.org)
                testimonialData.value = testimonialData.value.copy(user = user)
            }

            is TestimonialEvent.OnMediaSelect -> {
                if (event.url == null)
                    return

                when (event.mediaType) {
                    MediaType.BeforeImage -> {
                        val newMedia = testimonialData.value.media[0].copy(localUrl = event.url)
                        testimonialData.value = testimonialData.value.copy(
                            media = listOf(newMedia, testimonialData.value.media[1])
                        )
                    }

                    MediaType.AfterImage -> {
                        val newMedia = testimonialData.value.media[1].copy(localUrl = event.url)
                        testimonialData.value = testimonialData.value.copy(
                            media = listOf(testimonialData.value.media[0], newMedia)
                        )
                    }

                    MediaType.Video -> {
                        val newMedia = testimonialData.value.media[0].copy(localUrl = event.url)
                        testimonialData.value = testimonialData.value.copy(media = listOf(newMedia))
                    }
                }
            }

            is TestimonialEvent.OnMediaClear -> {
                when (event.mediaType) {
                    MediaType.BeforeImage -> {
                        val newMedia =
                            testimonialData.value.media[0].copy(localUrl = null, url = "")
                        testimonialData.value = testimonialData.value.copy(
                            media = listOf(newMedia, testimonialData.value.media[1])
                        )
                    }

                    MediaType.AfterImage -> {
                        val newMedia =
                            testimonialData.value.media[1].copy(localUrl = null, url = "")
                        testimonialData.value = testimonialData.value.copy(
                            media = listOf(testimonialData.value.media[0], newMedia)
                        )
                    }

                    MediaType.Video -> {
                        val newMedia =
                            testimonialData.value.media[0].copy(localUrl = null, url = "")
                        testimonialData.value = testimonialData.value.copy(
                            media = listOf(newMedia)
                        )
                    }
                }
            }

            is TestimonialEvent.OnSubmitTestimonial -> {
                postUserTestimonial()
            }
        }
    }
}
