package fit.asta.health.testimonials.viewmodel.create

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.R
import fit.asta.health.firebase.model.AuthRepo
import fit.asta.health.network.NetworkHelper
import fit.asta.health.testimonials.model.TestimonialRepo
import fit.asta.health.testimonials.model.domain.Media
import fit.asta.health.testimonials.model.domain.Testimonial
import fit.asta.health.testimonials.model.domain.TestimonialType
import fit.asta.health.testimonials.model.domain.TestimonialUser
import fit.asta.health.utils.UiString
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@ExperimentalCoroutinesApi
@HiltViewModel
class TestimonialViewModel
@Inject constructor(
    private val testimonialRepo: TestimonialRepo,
    private val authRepo: AuthRepo,
    val networkHelper: NetworkHelper
) : ViewModel() {

    var title by mutableStateOf(UiString.Resource(R.string.testimonial_title_create))
        private set

    var data by mutableStateOf(TestimonialData())
        private set

    private var curInx = 0

    private val _mutableState = MutableStateFlow<TestimonialGetState>(TestimonialGetState.Loading)
    val state = _mutableState.asStateFlow()

    private val _stateChannel = Channel<TestimonialSubmitState>()
    val stateChannel = _stateChannel.receiveAsFlow()

    init {
        onLoad()
    }

    fun onLoad() {
        if (networkHelper.isConnected()) {
            authRepo.getUser()?.let {
                loadTestimonial(userId = it.uid)
            }
        } else {
            _mutableState.value = TestimonialGetState.NoInternet
        }
    }

    private fun loadTestimonial(userId: String) {
        viewModelScope.launch {
            testimonialRepo.getTestimonial(userId).catch { ex ->
                //_mutableState.value = TestimonialGetState.Error(ex)
                _mutableState.value = TestimonialGetState.Empty
            }.collect {

                if (it.id.isNotBlank())
                    title = UiString.Resource(R.string.testimonial_title_edit)

                data = TestimonialData(
                    id = it.id,
                    type = it.type,
                    title = it.title,
                    testimonial = it.testimonial,
                    role = it.user.role,
                    org = it.user.org,
                    media = it.media
                )
                _mutableState.value = TestimonialGetState.Success(it)
            }
        }
    }

    private fun updateTestimonial(netTestimonial: Testimonial) {
        viewModelScope.launch {
            testimonialRepo.updateTestimonial(netTestimonial)
                .catch { ex ->
                    _stateChannel.send(TestimonialSubmitState.Error(ex))
                }.collect {
                    clearErrors()
                    _stateChannel.send(TestimonialSubmitState.Success(it))
                }
        }
    }

    fun onEvent(event: TestimonialEvent) {
        when (event) {
            is TestimonialEvent.OnTypeChange -> {
                //TODO - Proper handling of media based on testimonial type
                if (data.media.isEmpty()) {
                    data.media.add(0, Media(name = "0", title = "Before Image"))
                    data.media.add(1, Media(name = "1", title = "After Image"))
                }
                data = data.copy(type = event.type)
                data.enableSubmit = validateTestimonial(event.type)
            }
            is TestimonialEvent.OnTitleChange -> {
                data = data.copy(title = event.title)
                data.titleError = onValidateText(event.title, 4, 64)
                data.enableSubmit = validateTestimonial(data.type)
            }
            is TestimonialEvent.OnTestimonialChange -> {
                data = data.copy(testimonial = event.testimonial)
                data.testimonialError = onValidateText(event.testimonial, 32, 256)
                data.enableSubmit = validateTestimonial(data.type)
            }
            is TestimonialEvent.OnOrgChange -> {
                data = data.copy(org = event.org)
                data.orgError = onValidateText(event.org, 4, 32)
                data.enableSubmit = validateTestimonial(data.type)
            }
            is TestimonialEvent.OnRoleChange -> {
                data = data.copy(role = event.role)
                data.roleError = onValidateText(event.role, 2, 32)
                data.enableSubmit = validateTestimonial(data.type)
            }
            is TestimonialEvent.OnMediaClear -> {
                data.media[event.inx] = data.media[event.inx].copy(localUrl = null, url = "")
                data.enableSubmit = validateTestimonial(data.type)
            }
            is TestimonialEvent.OnSubmit -> {
                submit()
            }
            is TestimonialEvent.OnMediaIndex -> {
                curInx = event.inx
            }
            is TestimonialEvent.OnMediaSelect -> {
                if (event.url != null) {
                    data.media[curInx] = data.media[curInx].copy(localUrl = event.url)
                    data.enableSubmit = validateTestimonial(data.type)
                }
            }
        }
    }

    private fun submit() {

        //TODO - Error Handling and Validation required??

        authRepo.getUser()?.let {
            updateTestimonial(
                Testimonial(
                    id = data.id,
                    type = data.type,
                    title = data.title.trim(),
                    testimonial = data.testimonial.trim(),
                    userId = it.uid,
                    user = TestimonialUser(
                        name = it.name!!,
                        role = data.role.trim(),
                        org = data.org.trim(),
                        url = it.photoUrl.toString()
                    ),
                    media = data.media
                )
            )
        }
    }

    private fun clearErrors() {
        data.titleError = UiString.Empty
        data.testimonialError = UiString.Empty
        data.roleError = UiString.Empty
        data.orgError = UiString.Empty
    }

    private fun onValidateText(value: String, min: Int, max: Int): UiString {
        return when {
            value.isBlank() -> UiString.Resource(R.string.the_field_can_not_be_blank)
            value.length > max -> UiString.Resource(R.string.data_length_more, max)
            value.length in 1 until min -> UiString.Resource(R.string.data_length_less, min)
            else -> UiString.Empty
        }
    }

    private fun validateTestimonial(type: TestimonialType): Boolean {
        return when (type) {
            TestimonialType.TEXT -> validateData(
                data.testimonial.isNotBlank()
                        && data.testimonialError is UiString.Empty
            )
            TestimonialType.IMAGE -> validateData(
                data.testimonial.isNotBlank()
                        && data.testimonialError is UiString.Empty
                        && data.mediaError is UiString.Empty
            )
            TestimonialType.VIDEO -> validateData(
                data.testimonial.isNotBlank()
                        && data.testimonialError is UiString.Empty
                        && data.mediaError is UiString.Empty
            )
        }
    }

    private fun validateData(typeValidation: Boolean): Boolean {
        return (typeValidation && data.title.isNotBlank() && data.titleError is UiString.Empty
                && data.org.isNotBlank() && data.orgError is UiString.Empty
                && data.role.isNotBlank() && data.roleError is UiString.Empty
                )
    }
}