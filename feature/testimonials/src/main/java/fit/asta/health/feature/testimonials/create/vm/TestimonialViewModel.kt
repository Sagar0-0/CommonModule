package fit.asta.health.feature.testimonials.create.vm

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.auth.repo.AuthRepo
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.UiString
import fit.asta.health.common.utils.toUiState
import fit.asta.health.data.testimonials.model.CreateTestimonialResponse
import fit.asta.health.data.testimonials.model.InputWrapper
import fit.asta.health.data.testimonials.model.Media
import fit.asta.health.data.testimonials.model.Testimonial
import fit.asta.health.data.testimonials.model.TestimonialType
import fit.asta.health.data.testimonials.model.TestimonialUser
import fit.asta.health.data.testimonials.repo.TestimonialRepo
import fit.asta.health.feature.testimonials.create.vm.MediaType.AfterImage
import fit.asta.health.feature.testimonials.create.vm.MediaType.BeforeImage
import fit.asta.health.feature.testimonials.create.vm.MediaType.Video
import fit.asta.health.feature.testimonials.create.vm.TestimonialEvent.OnMediaClear
import fit.asta.health.feature.testimonials.create.vm.TestimonialEvent.OnMediaSelect
import fit.asta.health.feature.testimonials.create.vm.TestimonialEvent.OnOrgChange
import fit.asta.health.feature.testimonials.create.vm.TestimonialEvent.OnRoleChange
import fit.asta.health.feature.testimonials.create.vm.TestimonialEvent.OnSubmit
import fit.asta.health.feature.testimonials.create.vm.TestimonialEvent.OnTestimonialChange
import fit.asta.health.feature.testimonials.create.vm.TestimonialEvent.OnTitleChange
import fit.asta.health.feature.testimonials.create.vm.TestimonialEvent.OnTypeChange
import fit.asta.health.resources.strings.R
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

const val TESTIMONIAL_DATA = "testimonialData"
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
    private val savedState: SavedStateHandle
) : ViewModel() {

    private val _mutableState = MutableStateFlow<UiState<Testimonial>>(UiState.Loading)
    val state = _mutableState.asStateFlow()

    private val testimonialData = savedState.getStateFlow(
        TESTIMONIAL_DATA,
        Testimonial()
    )
    val id = savedState.getStateFlow(ID, "")
    val type = savedState.getStateFlow(
        TYPE,
        TestimonialType.from(0)
    )
    val title = savedState.getStateFlow(
        TITLE,
        InputWrapper()
    )
    val testimonial = savedState.getStateFlow(
        TESTIMONIAL,
        InputWrapper()
    )
    val org = savedState.getStateFlow(ORG, InputWrapper())
    val role = savedState.getStateFlow(ROLE, InputWrapper())
    val imgBefore =
        savedState.getStateFlow(
            IMAGE_BEFORE,
            Media(name = "before", title = "Before Image")
        )
    val imgAfter =
        savedState.getStateFlow(
            IMAGE_AFTER,
            Media(name = "after", title = "After Image")
        )
    val video =
        savedState.getStateFlow(
            VIDEO,
            Media(
                name = "journey",
                title = "Health Transformation"
            )
        )

    val areInputsValid =
        combine(type, title, testimonial, org, role) { type, title, testimonial, org, role ->
            when (type) {
                TestimonialType.TEXT -> testimonial.value.isNotBlank() && testimonial.error is UiString.Empty
                TestimonialType.IMAGE -> true
                TestimonialType.VIDEO -> true
            } && title.value.isNotEmpty() && title.error is UiString.Empty && org.value.isNotEmpty() && org.error is UiString.Empty && role.value.isNotEmpty() && role.error is UiString.Empty && isTestimonialDirty()
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(1000), false)


    val areMediaValid = combine(type, imgBefore, imgAfter, video) { type, before, after, video ->
        when (type) {
            TestimonialType.TEXT -> true
            TestimonialType.IMAGE -> (before.localUrl != null || before.url.isNotBlank()) && (after.localUrl != null || after.url.isNotBlank())
            TestimonialType.VIDEO -> video.localUrl != null || video.url.isNotBlank()
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(1000), false)


    private val _stateSubmit =
        MutableStateFlow<UiState<CreateTestimonialResponse>>(UiState.Loading)
    val stateSubmit = _stateSubmit.asStateFlow()


    init {
        loadTestimonial()
    }


    fun loadTestimonial() {
        viewModelScope.launch {
            authRepo.getUser()?.let { uid ->
                when (val result = testimonialRepo.getTestimonial(uid.uid)) {
                    is ResponseState.Success -> {
                        savedState[TESTIMONIAL_DATA] = result.data
                        savedState[ID] = result.data.id
                        savedState[TYPE] =
                            TestimonialType.from(result.data.type)
                        savedState[TITLE] =
                            InputWrapper(value = result.data.title)
                        savedState[TESTIMONIAL] =
                            InputWrapper(value = result.data.testimonial)
                        savedState[ORG] =
                            InputWrapper(value = result.data.user.org)
                        savedState[ROLE] =
                            InputWrapper(value = result.data.user.role)

                        when (TestimonialType.from(
                            result.data.type
                        )) {
                            TestimonialType.TEXT -> {
                            }

                            TestimonialType.IMAGE -> {
                                savedState[IMAGE_BEFORE] =
                                    Media(url = result.data.media[0].url)
                                savedState[IMAGE_AFTER] =
                                    Media(url = result.data.media[1].url)
                            }

                            TestimonialType.VIDEO -> {
                                savedState[VIDEO] =
                                    Media(url = result.data.media[0].url)
                            }
                        }
                        _mutableState.value = UiState.Success(result.data)
                    }

                    else -> {
                        _mutableState.value = result.toUiState()
                    }
                }
            }
        }
    }


    private fun submit() {
        authRepo.getUser()?.let {
            updateTestimonial(
                Testimonial(
                    id = id.value,
                    type = type.value.value,
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
                            imgBefore.value,
                            imgAfter.value
                        )

                        TestimonialType.VIDEO -> listOf(
                            video.value
                        )
                    }
                )
            )
        }
    }

    private fun updateTestimonial(netTestimonial: Testimonial) {
        viewModelScope.launch {
            _stateSubmit.value = testimonialRepo.updateTestimonial(netTestimonial).toUiState()
        }
    }

    fun onEvent(event: TestimonialEvent) {
        when (event) {
            is OnTypeChange -> {
                savedState[TYPE] = event.type
            }

            is OnTitleChange -> {
                savedState[TITLE] = title.value.copy(
                    value = event.title, error = onValidateText(event.title, 4, 64)
                )
            }

            is OnTestimonialChange -> {
                savedState[TESTIMONIAL] = testimonial.value.copy(
                    value = event.testimonial, error = onValidateText(event.testimonial, 32, 256)
                )
            }

            is OnOrgChange -> {
                savedState[ORG] =
                    title.value.copy(value = event.org, error = onValidateText(event.org, 4, 32))
            }

            is OnRoleChange -> {
                savedState[ROLE] =
                    title.value.copy(value = event.role, error = onValidateText(event.role, 2, 32))
            }

            is OnMediaSelect -> {
                if (event.url == null) {
                    return
                }
                when (event.mediaType) {
                    AfterImage -> {
                        savedState[IMAGE_AFTER] = imgAfter.value.copy(
                            localUrl = event.url,
                            error = onValidateMedia(imgAfter.value.localUrl, imgAfter.value.url)
                        )
                    }

                    BeforeImage -> {
                        savedState[IMAGE_BEFORE] = imgBefore.value.copy(
                            localUrl = event.url,
                            error = onValidateMedia(imgBefore.value.localUrl, imgBefore.value.url)
                        )
                    }

                    Video -> {
                        savedState[VIDEO] = video.value.copy(
                            localUrl = event.url,
                            error = onValidateMedia(video.value.localUrl, video.value.url)
                        )
                    }
                }
            }

            is OnMediaClear -> {
                when (event.mediaType) {
                    AfterImage -> {
                        savedState[IMAGE_AFTER] = imgAfter.value.copy(
                            localUrl = null,
                            url = "",
                            error = UiString.Resource(R.string.the_media_can_not_be_blank)
                        )
                    }

                    BeforeImage -> {
                        savedState[IMAGE_BEFORE] = imgBefore.value.copy(
                            localUrl = null,
                            url = "",
                            error = UiString.Resource(R.string.the_media_can_not_be_blank)
                        )
                    }

                    Video -> {
                        savedState[VIDEO] = video.value.copy(
                            localUrl = null,
                            url = "",
                            error = UiString.Resource(R.string.the_media_can_not_be_blank)
                        )
                    }
                }
            }

            is OnSubmit -> {
                submit()
            }
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
                R.string.data_length_less, min.toString()
            )

            else -> UiString.Empty
        }
    }

    private fun onValidateMedia(localUrl: Uri?, url: String): UiString {
        return if (localUrl != null || url.isNotBlank()) UiString.Empty
        else UiString.Resource(R.string.the_media_can_not_be_blank)
    }

    private fun isTestimonialDirty(): Boolean {
        return testimonialData.value.title != title.value.value || testimonialData.value.testimonial != testimonial.value.value || testimonialData.value.user.org != org.value.value || testimonialData.value.user.role != role.value.value
    }


}