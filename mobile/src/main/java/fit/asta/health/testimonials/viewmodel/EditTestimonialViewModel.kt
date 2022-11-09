package fit.asta.health.testimonials.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.firebase.model.AuthRepo
import fit.asta.health.testimonials.model.TestimonialRepo
import fit.asta.health.testimonials.model.network.NetTestimonial
import fit.asta.health.testimonials.model.network.User
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
class EditTestimonialViewModel
@Inject constructor(
    private val testimonialRepo: TestimonialRepo,
    private val authRepo: AuthRepo,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    var stateSave by mutableStateOf(EditTestimonialState())
        private set

    private val mutableState = MutableStateFlow<GetTestimonialState>(GetTestimonialState.Loading)
    val state = mutableState.asStateFlow()

    private val _validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = _validationEventChannel.receiveAsFlow()

    var testimonialId = ""

    init {

        authRepo.getUser()?.let {
            loadTestimonial(userId = it.uid)
        }
    }

    private fun loadTestimonial(userId: String) {
        viewModelScope.launch {
            testimonialRepo.getTestimonial(userId).catch { exception ->
                mutableState.value = GetTestimonialState.Error(exception)
            }.collect {
                testimonialId = it.testimonial.id
                mutableState.value = GetTestimonialState.Success(it.testimonial)
            }
        }
    }

    private fun updateTestimonial(netTestimonial: NetTestimonial) {
        viewModelScope.launch {
            testimonialRepo.updateTestimonial(netTestimonial).catch { exception ->
                mutableState.value = GetTestimonialState.Error(exception)
            }.collect {
                mutableState.value = GetTestimonialState.Update(it)
                _validationEventChannel.send(ValidationEvent.Success)
            }
        }
    }

    fun onEvent(event: EditTestimonialEvent) {
        when (event) {
            is EditTestimonialEvent.OnTypeChange -> {
                stateSave = stateSave.copy(type = event.type)
            }
            is EditTestimonialEvent.OnTitleChange -> {
                stateSave = stateSave.copy(title = event.title)
            }
            is EditTestimonialEvent.OnSubTitleChange -> {
                stateSave = stateSave.copy(subTitle = event.subTitle)
            }
            is EditTestimonialEvent.OnTestimonialChange -> {
                stateSave = stateSave.copy(testimonial = event.testimonial)
            }
            is EditTestimonialEvent.OnRoleChange -> {
                stateSave = stateSave.copy(role = event.role)
            }
            is EditTestimonialEvent.OnOrgChange -> {
                stateSave = stateSave.copy(organization = event.org)
            }
            is EditTestimonialEvent.OnSubmit -> submit()
        }
    }

    private fun submit() {

        //TODO - Error Handling and Validation required

        authRepo.getUser()?.let {
            updateTestimonial(
                NetTestimonial(
                    id = testimonialId,
                    apv = false,
                    rank = -1,
                    title = stateSave.title,
                    testimonial = stateSave.testimonial,
                    uid = it.uid,
                    user = User(
                        name = it.name!!,
                        role = stateSave.role,
                        org = stateSave.organization,
                        url = it.photoUrl.toString()
                    ),
                    media = listOf()
                )
            )
        }
    }

    sealed class ValidationEvent {
        object Success : ValidationEvent()
    }
}