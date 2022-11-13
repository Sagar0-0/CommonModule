package fit.asta.health.testimonials.viewmodel.create

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
                    organization = it.testimonial.user.org
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
                _stateChannel.send(TestimonialSubmitState.Success(it))
            }
        }
    }

    fun onEvent(event: TestimonialEvent) {
        when (event) {
            is TestimonialEvent.OnTypeChange -> {
                this.data = this.data.copy(type = event.type)
            }
            is TestimonialEvent.OnTitleChange -> {
                this.data = this.data.copy(title = event.title)
            }
            is TestimonialEvent.OnTestimonialChange -> {
                this.data = this.data.copy(testimonial = event.testimonial)
            }
            is TestimonialEvent.OnRoleChange -> {
                this.data = this.data.copy(role = event.role)
            }
            is TestimonialEvent.OnOrgChange -> {
                this.data = this.data.copy(organization = event.org)
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
                    title = this.data.title,
                    testimonial = this.data.testimonial,
                    userId = it.uid,
                    user = NetTestimonialUser(
                        name = it.name!!,
                        role = this.data.role,
                        org = this.data.organization,
                        url = it.photoUrl.toString()
                    ),
                    media = listOf()
                )
            )
        }
    }
}