package fit.asta.health.testimonials.viewmodel.create

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.R
import fit.asta.health.firebase.model.AuthRepo
import fit.asta.health.network.NetworkHelper
import fit.asta.health.testimonials.model.TestimonialRepo
import fit.asta.health.testimonials.model.domain.*
import fit.asta.health.utils.UiString
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

const val TYPE = "type"
const val TITLE = "title"
const val TESTIMONIAL = "testimonial"
const val ORG = "org"
const val ROLE = "role"

@ExperimentalCoroutinesApi
@HiltViewModel
class TestimonialViewModel
@Inject constructor(
    private val testimonialRepo: TestimonialRepo,
    private val authRepo: AuthRepo,
    val networkHelper: NetworkHelper,
    private val savedState: SavedStateHandle
) : ViewModel() {

    var titleMain by mutableStateOf(UiString.Resource(R.string.testimonial_title_create))
        private set

    val type = savedState.getStateFlow<TestimonialType>(TYPE, TestimonialType.TEXT)
    val title = savedState.getStateFlow(TITLE, InputWrapper())
    val testimonial = savedState.getStateFlow(TESTIMONIAL, InputWrapper())
    val org = savedState.getStateFlow(ORG, InputWrapper())
    val role = savedState.getStateFlow(ROLE, InputWrapper())

    val areInputsValid = combine(title, testimonial, org, role) { title, testimonial, org, role ->

        title.value.isNotEmpty() && title.error == UiString.Empty &&
                testimonial.value.isNotEmpty() && testimonial.error == UiString.Empty &&
                org.value.isNotEmpty() && org.error == UiString.Empty &&
                role.value.isNotEmpty() && role.error == UiString.Empty

    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

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
                    titleMain = UiString.Resource(R.string.testimonial_title_edit)

                savedState[TYPE] = it.type
                savedState[TITLE] = it.title
                savedState[TESTIMONIAL] = it.testimonial
                savedState[ORG] = it.user.org
                savedState[ROLE] = it.user.role

                data = TestimonialData(
                    id = it.id
                )

                when (it.type) {
                    TestimonialType.TEXT -> {
                    }
                    TestimonialType.IMAGE -> {
                        data.imgMedia = it.media.toMutableStateList()
                    }
                    TestimonialType.VIDEO -> {
                        data.vdoMedia = it.media.toMutableStateList()
                    }
                }

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
                savedState[TYPE] = event.type
                //_areInputsValid.value = validateTestimonial(event.type)
            }
            is TestimonialEvent.OnTitleChange -> {
                savedState[TITLE] = title.value.copy(value = event.title, error = onValidateText(event.title, 4, 64))
            }
            is TestimonialEvent.OnTestimonialChange -> {
                savedState[TESTIMONIAL] =
                    testimonial.value.copy(value = event.testimonial, error = onValidateText(event.testimonial, 32, 256))
            }
            is TestimonialEvent.OnOrgChange -> {
                savedState[ORG] = title.value.copy(value = event.org, error = onValidateText(event.org, 4, 32))
            }
            is TestimonialEvent.OnRoleChange -> {
                savedState[ROLE] = title.value.copy(value = event.role, error = onValidateText(event.role, 2, 32))
            }
            is TestimonialEvent.OnMediaIndex -> {
                curInx = event.inx
            }
            is TestimonialEvent.OnImageSelect -> {
                if (event.url != null) {
                    data.imgMedia[curInx] = data.imgMedia[curInx].copy(localUrl = event.url)
                }
                data.imgError = onValidateMedia(data.imgMedia)
                //_areInputsValid.value = validateTestimonial(type.value)
            }
            is TestimonialEvent.OnImageClear -> {
                data.imgMedia[event.inx] = data.imgMedia[event.inx].copy(localUrl = null, url = "")
                data.imgError = UiString.Resource(R.string.the_media_can_not_be_blank)
                //_areInputsValid.value = validateTestimonial(type.value)
            }
            is TestimonialEvent.OnVideoSelect -> {
                if (event.url != null) {
                    data.vdoMedia[curInx] = data.vdoMedia[curInx].copy(localUrl = event.url)
                }
                data.vdoError = onValidateMedia(data.vdoMedia)
                //_areInputsValid.value = validateTestimonial(type.value)
            }
            is TestimonialEvent.OnVideoClear -> {
                data.vdoMedia[event.inx] = data.vdoMedia[event.inx].copy(localUrl = null, url = "")
                data.vdoError = UiString.Resource(R.string.the_media_can_not_be_blank)
                //_areInputsValid.value = validateTestimonial(type.value)
            }
            is TestimonialEvent.OnSubmit -> {
                submit()
            }
        }
    }

    private fun submit() {

        authRepo.getUser()?.let {
            updateTestimonial(
                Testimonial(
                    id = data.id,
                    type = type.value,
                    title = title.value.value.trim(),
                    testimonial = testimonial.value.value.trim(),
                    userId = it.uid,
                    user = TestimonialUser(
                        name = it.name!!,
                        role = role.value.value.trim(),
                        org = org.value.value.trim(),
                        url = it.photoUrl.toString()
                    ),
                    media = when (type.value) {
                        TestimonialType.TEXT -> listOf()
                        TestimonialType.IMAGE -> data.imgMedia
                        TestimonialType.VIDEO -> data.vdoMedia
                    }
                )
            )
        }
    }

    private fun clearErrors() {
        savedState[TITLE] = title.value.copy(error = UiString.Empty)
        savedState[TESTIMONIAL] = testimonial.value.copy(error = UiString.Empty)
        savedState[ORG] = org.value.copy(error = UiString.Empty)
        savedState[ROLE] = role.value.copy(error = UiString.Empty)

        data.imgError = UiString.Empty
        data.vdoError = UiString.Empty
    }

    private fun onValidateText(value: String, min: Int, max: Int): UiString {
        return when {
            value.isBlank() -> UiString.Resource(R.string.the_field_can_not_be_blank)
            value.length > max -> UiString.Resource(R.string.data_length_more, max.toString())
            value.length in 1 until min -> UiString.Resource(R.string.data_length_less, min.toString())
            else -> UiString.Empty
        }
    }

    private fun onValidateMedia(mediaList: List<Media>): UiString {
        return if (validateMedia(mediaList)) UiString.Empty
        else UiString.Resource(R.string.the_media_can_not_be_blank)
    }

    private fun validateMedia(mediaList: List<Media>): Boolean {
        return mediaList.find { it.localUrl == null && it.url.isBlank() } == null
    }

    /*private suspend fun clearFocusAndHideKeyboard() {
        _events.send(ScreenEvent.ClearFocus)
        _events.send(ScreenEvent.UpdateKeyboard(false))
        focusedTextField = FocusedTextFieldKey.NONE
    }

    private fun focusOnLastSelectedTextField() {
        viewModelScope.launch(Dispatchers.Default) {
            _events.send(ScreenEvent.RequestFocus(focusedTextField))
            delay(250)
            _events.send(ScreenEvent.UpdateKeyboard(true))
        }
    }*/
}