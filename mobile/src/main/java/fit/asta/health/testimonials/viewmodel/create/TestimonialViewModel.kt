package fit.asta.health.testimonials.viewmodel.create

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.R
import fit.asta.health.firebase.model.AuthRepo
import fit.asta.health.network.repo.FileUploadRepo
import fit.asta.health.testimonials.model.TestimonialRepo
import fit.asta.health.testimonials.model.network.NetTestimonial
import fit.asta.health.testimonials.model.network.NetTestimonialUser
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
    private val fileRepo: FileUploadRepo,
    private val authRepo: AuthRepo
) : ViewModel() {

    var data by mutableStateOf(TestimonialState())
        private set

    private val _mutableState = MutableStateFlow<TestimonialGetState>(TestimonialGetState.Loading)
    val state = _mutableState.asStateFlow()

    private val _stateChannel = Channel<TestimonialSubmitState>()
    val stateChannel = _stateChannel.receiveAsFlow()

    init {
        authRepo.getUser()?.let {
            loadTestimonial(userId = it.uid)
        }
    }

    private fun loadTestimonial(userId: String) {
        viewModelScope.launch {
            testimonialRepo.getTestimonial(userId).catch {
                //_mutableState.value = GetTestimonialState.Error(exception)
                _mutableState.value = TestimonialGetState.Empty
            }.collect {
                data = TestimonialState(
                    id = it.testimonial.id,
                    type = it.testimonial.type,
                    title = it.testimonial.title,
                    testimonial = it.testimonial.testimonial,
                    role = it.testimonial.user.role,
                    org = it.testimonial.user.org
                )
                _mutableState.value = TestimonialGetState.Success(it.testimonial)
            }
        }
    }

    private fun updateTestimonial(netTestimonial: NetTestimonial) {
        viewModelScope.launch {
            testimonialRepo.updateTestimonial(netTestimonial).catch { exception ->
                _stateChannel.send(TestimonialSubmitState.Error(exception))
            }.collect {
                clearErrors()
                _stateChannel.send(TestimonialSubmitState.Success(it))
            }
        }
    }

    fun onEvent(event: TestimonialEvent) {
        when (event) {
            is TestimonialEvent.OnTypeChange -> {
                this.data = this.data.copy(type = event.type)
                this.data.enableSubmit = validateData()
            }
            is TestimonialEvent.OnTitleChange -> {
                this.data.titleError = onValidateText(event.title, 4, 64)
                this.data = this.data.copy(title = event.title)
                this.data.enableSubmit = validateData()
            }
            is TestimonialEvent.OnTestimonialChange -> {
                this.data.testimonialError = onValidateText(event.testimonial, 32, 512)
                this.data = this.data.copy(testimonial = event.testimonial)
                this.data.enableSubmit = validateData()
            }
            is TestimonialEvent.OnOrgChange -> {
                this.data.orgError = onValidateText(event.org, 4, 32)
                this.data = this.data.copy(org = event.org)
                this.data.enableSubmit = validateData()
            }
            is TestimonialEvent.OnRoleChange -> {
                this.data.roleError = onValidateText(event.role, 2, 32)
                this.data = this.data.copy(role = event.role)
                this.data.enableSubmit = validateData()
            }
            is TestimonialEvent.OnSubmit -> submit()
        }
    }

    private fun submit() {

        //TODO - Error Handling and Validation required??

        authRepo.getUser()?.let {
            updateTestimonial(
                NetTestimonial(
                    id = this.data.id,
                    type = this.data.type,
                    apv = false,
                    rank = -1,
                    title = this.data.title.trim(),
                    testimonial = this.data.testimonial.trim(),
                    userId = it.uid,
                    user = NetTestimonialUser(
                        name = it.name!!,
                        role = this.data.role.trim(),
                        org = this.data.org.trim(),
                        url = it.photoUrl.toString()
                    ),
                    media = listOf()
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

    private fun validateData(): Boolean {
        return (data.title.isNotBlank() && data.titleError is UiString.Empty
                && data.testimonial.isNotBlank() && data.testimonialError is UiString.Empty
                && data.org.isNotBlank() && data.orgError is UiString.Empty
                && data.role.isNotBlank() && data.roleError is UiString.Empty
                )
    }

    private fun onValidateTestimonial() {
        if (data.testimonial.isBlank() || data.testimonial.length > 512)
            data.testimonialError = UiString.Resource(R.string.the_field_can_not_be_blank)
    }
}