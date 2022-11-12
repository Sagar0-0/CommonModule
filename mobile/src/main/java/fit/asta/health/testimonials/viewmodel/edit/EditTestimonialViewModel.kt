package fit.asta.health.testimonials.viewmodel.edit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.firebase.model.AuthRepo
import fit.asta.health.testimonials.model.TestimonialRepo
import fit.asta.health.testimonials.model.network.NetTestimonial
import fit.asta.health.testimonials.model.network.NetTestimonialUser
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@ExperimentalCoroutinesApi
@HiltViewModel
class EditTestimonialViewModel
@Inject constructor(
    private val testimonialRepo: TestimonialRepo,
    private val authRepo: AuthRepo
) : ViewModel() {

    var state by mutableStateOf(TestimonialState())
        private set

    private val _stateChannel = Channel<TestimonialSubmitState>()
    val stateChannel = _stateChannel.receiveAsFlow()

    init {
        authRepo.getUser()?.let {
            loadTestimonial(userId = it.uid)
        }
    }

    private fun loadTestimonial(userId: String) {
        viewModelScope.launch {
            testimonialRepo.getTestimonial(userId).catch { exception ->
                _stateChannel.send(TestimonialSubmitState.Error(exception))
            }.collect {
                state = TestimonialState(
                    id = it.testimonial.id,
                    type = it.testimonial.type,
                    title = it.testimonial.title,
                    testimonial = it.testimonial.testimonial,
                    role = it.testimonial.user.role,
                    organization = it.testimonial.user.org
                )
            }
        }
    }

    private fun updateTestimonial(netTestimonial: NetTestimonial) {
        viewModelScope.launch {
            testimonialRepo.updateTestimonial(netTestimonial).catch { exception ->
                _stateChannel.send(TestimonialSubmitState.Error(exception))
            }.collect {
                _stateChannel.send(TestimonialSubmitState.Success(it))
            }
        }
    }

    fun onEvent(event: EditTestimonialEvent) {
        when (event) {
            is EditTestimonialEvent.OnTypeChange -> {
                this.state = this.state.copy(type = event.type)
            }
            is EditTestimonialEvent.OnTitleChange -> {
                this.state = this.state.copy(title = event.title)
            }
            is EditTestimonialEvent.OnTestimonialChange -> {
                this.state = this.state.copy(testimonial = event.testimonial)
            }
            is EditTestimonialEvent.OnRoleChange -> {
                this.state = this.state.copy(role = event.role)
            }
            is EditTestimonialEvent.OnOrgChange -> {
                this.state = this.state.copy(organization = event.org)
            }
            is EditTestimonialEvent.OnSubmit -> submit()
        }
    }

    private fun submit() {

        //TODO - Error Handling and Validation required??

        authRepo.getUser()?.let {
            updateTestimonial(
                NetTestimonial(
                    id = this.state.id,
                    type = this.state.type,
                    apv = false,
                    rank = -1,
                    title = this.state.title,
                    testimonial = this.state.testimonial,
                    userId = it.uid,
                    user = NetTestimonialUser(
                        name = it.name!!,
                        role = this.state.role,
                        org = this.state.organization,
                        url = it.photoUrl.toString()
                    ),
                    media = listOf()
                )
            )
        }
    }
}