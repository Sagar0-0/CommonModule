package fit.asta.health.testimonials.viewmodel.create

import android.net.Uri
import androidx.compose.runtime.*
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


const val ID = "id"
const val TYPE = "type"
const val TITLE = "title"
const val TESTIMONIAL = "testimonial"
const val ORG = "org"
const val ROLE = "role"
const val IMAGE_BEFORE = "img_before"
const val IMAGE_AFTER = "img_after"
const val VIDEO = "video"

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

    private val _mutableState = MutableStateFlow<TestimonialGetState>(TestimonialGetState.Loading)
    val state = _mutableState.asStateFlow()

    val id = savedState.getStateFlow(ID, "")
    val type = savedState.getStateFlow<TestimonialType>(TYPE, TestimonialType.TEXT)
    val title = savedState.getStateFlow(TITLE, InputWrapper())
    val testimonial = savedState.getStateFlow(TESTIMONIAL, InputWrapper())
    val org = savedState.getStateFlow(ORG, InputWrapper())
    val role = savedState.getStateFlow(ROLE, InputWrapper())
    val imgBefore = savedState.getStateFlow(IMAGE_BEFORE, MediaWrapper(name = "before", title = "Before Image"))
    val imgAfter = savedState.getStateFlow(IMAGE_AFTER, MediaWrapper(name = "after", title = "After Image"))
    val video = savedState.getStateFlow(VIDEO, MediaWrapper(name = "journey", title = "Health Transformation"))

    val areInputsValid =
        combine(type, title, testimonial, org, role) { type, title, testimonial, org, role ->

            when (type) {
                TestimonialType.TEXT -> testimonial.value.isNotBlank() && testimonial.error is UiString.Empty
                TestimonialType.IMAGE -> true
                TestimonialType.VIDEO -> true
            } && title.value.isNotEmpty() && title.error is UiString.Empty &&
                    org.value.isNotEmpty() && org.error is UiString.Empty &&
                    role.value.isNotEmpty() && role.error is UiString.Empty

        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(1000), false)

    val areMediaValid = combine(type, imgBefore, imgAfter, video) { type, before, after, video ->

        when (type) {
            TestimonialType.TEXT -> true
            TestimonialType.IMAGE -> (before.localUrl != null || before.url.isNotBlank()) &&
                    (after.localUrl != null || after.url.isNotBlank())
            TestimonialType.VIDEO -> video.localUrl != null || video.url.isNotBlank()
        }

    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(1000), false)

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

                savedState[ID] = it.id
                savedState[TYPE] = it.type
                savedState[TITLE] = InputWrapper(value = it.title)
                savedState[TESTIMONIAL] = InputWrapper(value = it.testimonial)
                savedState[ORG] = InputWrapper(value = it.user.org)
                savedState[ROLE] = InputWrapper(value = it.user.role)

                when (it.type) {
                    TestimonialType.TEXT -> {
                    }
                    TestimonialType.IMAGE -> {
                        savedState[IMAGE_BEFORE] = MediaWrapper(url = it.media[0].url)
                        savedState[IMAGE_AFTER] = MediaWrapper(url = it.media[1].url)
                    }
                    TestimonialType.VIDEO -> {
                        savedState[VIDEO] = MediaWrapper(url = it.media[0].url)
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
            }
            is TestimonialEvent.OnTitleChange -> {
                savedState[TITLE] = title.value.copy(
                    value = event.title,
                    error = onValidateText(event.title, 4, 64)
                )
            }
            is TestimonialEvent.OnTestimonialChange -> {
                savedState[TESTIMONIAL] =
                    testimonial.value.copy(
                        value = event.testimonial,
                        error = onValidateText(event.testimonial, 32, 256)
                    )
            }
            is TestimonialEvent.OnOrgChange -> {
                savedState[ORG] =
                    title.value.copy(value = event.org, error = onValidateText(event.org, 4, 32))
            }
            is TestimonialEvent.OnRoleChange -> {
                savedState[ROLE] =
                    title.value.copy(value = event.role, error = onValidateText(event.role, 2, 32))
            }
            is TestimonialEvent.OnMediaSelect -> {

                if (event.url == null) {
                    return
                }

                when (event.mediaType) {
                    MediaType.AfterImage -> {
                        savedState[IMAGE_AFTER] = imgAfter.value.copy(
                            localUrl = event.url,
                            error = onValidateMedia(imgAfter.value.localUrl, imgAfter.value.url)
                        )
                    }
                    MediaType.BeforeImage -> {
                        savedState[IMAGE_BEFORE] = imgBefore.value.copy(
                            localUrl = event.url,
                            error = onValidateMedia(imgBefore.value.localUrl, imgBefore.value.url)
                        )
                    }
                    MediaType.Video -> {
                        savedState[VIDEO] = video.value.copy(
                            localUrl = event.url,
                            error = onValidateMedia(video.value.localUrl, video.value.url)
                        )
                    }
                }
            }
            is TestimonialEvent.OnMediaClear -> {

                when (event.mediaType) {
                    MediaType.AfterImage -> {
                        savedState[IMAGE_AFTER] = imgAfter.value.copy(
                            localUrl = null, url = "",
                            error = UiString.Resource(R.string.the_media_can_not_be_blank)
                        )
                    }
                    MediaType.BeforeImage -> {
                        savedState[IMAGE_BEFORE] = imgBefore.value.copy(
                            localUrl = null, url = "",
                            error = UiString.Resource(R.string.the_media_can_not_be_blank)
                        )
                    }
                    MediaType.Video -> {
                        savedState[VIDEO] = video.value.copy(
                            localUrl = null, url = "",
                            error = UiString.Resource(R.string.the_media_can_not_be_blank)
                        )
                    }
                }
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
                    id = id.value,
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
                        TestimonialType.IMAGE -> listOf(
                            Media(
                                name = imgBefore.value.name,
                                title = imgBefore.value.title,
                                localUrl = imgBefore.value.localUrl,
                                url = imgBefore.value.url
                            ),
                            Media(
                                name = imgAfter.value.name,
                                title = imgAfter.value.title,
                                localUrl = imgAfter.value.localUrl,
                                url = imgAfter.value.url
                            )
                        )
                        TestimonialType.VIDEO -> listOf(
                            Media(
                                name = video.value.name,
                                title = video.value.title,
                                localUrl = video.value.localUrl,
                                url = video.value.url
                            )
                        )
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
        savedState[IMAGE_BEFORE] = imgBefore.value.copy(error = UiString.Empty)
        savedState[IMAGE_AFTER] = imgAfter.value.copy(error = UiString.Empty)
        savedState[VIDEO] = video.value.copy(error = UiString.Empty)
    }

    private fun onValidateText(value: String, min: Int, max: Int): UiString {
        return when {
            value.isBlank() -> UiString.Resource(R.string.the_field_can_not_be_blank)
            value.length > max -> UiString.Resource(R.string.data_length_more, max.toString())
            value.length in 1 until min -> UiString.Resource(
                R.string.data_length_less,
                min.toString()
            )
            else -> UiString.Empty
        }
    }

    private fun onValidateMedia(localUrl: Uri?, url: String): UiString {
        return if (localUrl != null || url.isNotBlank()) UiString.Empty
        else UiString.Resource(R.string.the_media_can_not_be_blank)
    }
}