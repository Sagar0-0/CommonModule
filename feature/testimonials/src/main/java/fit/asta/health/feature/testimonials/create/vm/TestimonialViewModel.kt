package fit.asta.health.feature.testimonials.create.vm

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.Player
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.auth.repo.AuthRepo
import fit.asta.health.common.utils.UiString
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
import fit.asta.health.feature.testimonials.create.vm.TestimonialGetState.Empty
import fit.asta.health.feature.testimonials.create.vm.TestimonialGetState.Error
import fit.asta.health.feature.testimonials.create.vm.TestimonialGetState.Loading
import fit.asta.health.feature.testimonials.create.vm.TestimonialGetState.NetworkError
import fit.asta.health.feature.testimonials.create.vm.TestimonialGetState.Success
import fit.asta.health.network.data.ApiResponse
import fit.asta.health.resources.strings.R
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.IOException
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
    private val testimonialRepo: fit.asta.health.data.testimonials.repo.TestimonialRepo,
    private val authRepo: AuthRepo,
    private val savedState: SavedStateHandle,
    private val player: Player
) : ViewModel() {

    private val _mutableState = MutableStateFlow<TestimonialGetState>(Loading)
    val state = _mutableState.asStateFlow()

    private val testimonialData = savedState.getStateFlow(
        TESTIMONIAL_DATA,
        fit.asta.health.data.testimonials.model.Testimonial()
    )
    val id = savedState.getStateFlow(ID, "")
    val type = savedState.getStateFlow(
        TYPE,
        fit.asta.health.data.testimonials.model.TestimonialType.from(0)
    )
    val title = savedState.getStateFlow(
        TITLE,
        fit.asta.health.data.testimonials.model.InputWrapper()
    )
    val testimonial = savedState.getStateFlow(
        TESTIMONIAL,
        fit.asta.health.data.testimonials.model.InputWrapper()
    )
    val org = savedState.getStateFlow(ORG, fit.asta.health.data.testimonials.model.InputWrapper())
    val role = savedState.getStateFlow(ROLE, fit.asta.health.data.testimonials.model.InputWrapper())
    val imgBefore =
        savedState.getStateFlow(
            IMAGE_BEFORE,
            fit.asta.health.data.testimonials.model.Media(name = "before", title = "Before Image")
        )
    val imgAfter =
        savedState.getStateFlow(
            IMAGE_AFTER,
            fit.asta.health.data.testimonials.model.Media(name = "after", title = "After Image")
        )
    val video =
        savedState.getStateFlow(
            VIDEO,
            fit.asta.health.data.testimonials.model.Media(
                name = "journey",
                title = "Health Transformation"
            )
        )

    val areInputsValid =
        combine(type, title, testimonial, org, role) { type, title, testimonial, org, role ->
            when (type) {
                fit.asta.health.data.testimonials.model.TestimonialType.TEXT -> testimonial.value.isNotBlank() && testimonial.error is UiString.Empty
                fit.asta.health.data.testimonials.model.TestimonialType.IMAGE -> true
                fit.asta.health.data.testimonials.model.TestimonialType.VIDEO -> true
            } && title.value.isNotEmpty() && title.error is UiString.Empty && org.value.isNotEmpty() && org.error is UiString.Empty && role.value.isNotEmpty() && role.error is UiString.Empty && isTestimonialDirty()
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(1000), false)


    val areMediaValid = combine(type, imgBefore, imgAfter, video) { type, before, after, video ->
        when (type) {
            fit.asta.health.data.testimonials.model.TestimonialType.TEXT -> true
            fit.asta.health.data.testimonials.model.TestimonialType.IMAGE -> (before.localUrl != null || before.url.isNotBlank()) && (after.localUrl != null || after.url.isNotBlank())
            fit.asta.health.data.testimonials.model.TestimonialType.VIDEO -> video.localUrl != null || video.url.isNotBlank()
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(1000), false)


    private val _stateSubmit =
        MutableStateFlow<TestimonialSubmitState>(TestimonialSubmitState.Loading)
    val stateSubmit = _stateSubmit.asStateFlow()


    init {
        loadTestimonial()
    }


    fun loadTestimonial() {
        viewModelScope.launch {
            try {
                authRepo.getUser()?.let { uid ->
                    when (val result = testimonialRepo.getTestimonial(uid.uid)) {
                        is ApiResponse.Error -> {
                        }
                        is ApiResponse.HttpError -> {
                            _mutableState.value = Empty
                        }
                        is ApiResponse.Success -> {
                            savedState[TESTIMONIAL_DATA] = result.data
                            savedState[ID] = result.data.id
                            savedState[TYPE] =
                                fit.asta.health.data.testimonials.model.TestimonialType.from(result.data.type)
                            savedState[TITLE] =
                                fit.asta.health.data.testimonials.model.InputWrapper(value = result.data.title)
                            savedState[TESTIMONIAL] =
                                fit.asta.health.data.testimonials.model.InputWrapper(value = result.data.testimonial)
                            savedState[ORG] =
                                fit.asta.health.data.testimonials.model.InputWrapper(value = result.data.user.org)
                            savedState[ROLE] =
                                fit.asta.health.data.testimonials.model.InputWrapper(value = result.data.user.role)

                            when (fit.asta.health.data.testimonials.model.TestimonialType.from(
                                result.data.type
                            )) {
                                fit.asta.health.data.testimonials.model.TestimonialType.TEXT -> {
                                }

                                fit.asta.health.data.testimonials.model.TestimonialType.IMAGE -> {
                                    savedState[IMAGE_BEFORE] =
                                        fit.asta.health.data.testimonials.model.Media(url = result.data.media[0].url)
                                    savedState[IMAGE_AFTER] =
                                        fit.asta.health.data.testimonials.model.Media(url = result.data.media[1].url)
                                }

                                fit.asta.health.data.testimonials.model.TestimonialType.VIDEO -> {
                                    savedState[VIDEO] =
                                        fit.asta.health.data.testimonials.model.Media(url = result.data.media[0].url)
                                }
                            }
                            _mutableState.value = Success(result.data)
                        }
                    }
                }
            } catch (networkException: IOException) {
                _mutableState.value = NetworkError(networkException)
            } catch (exception: Exception) {
                _mutableState.value = Error(exception)
            }


        }
    }


    private fun submit() {
        authRepo.getUser()?.let {
            updateTestimonial(
                fit.asta.health.data.testimonials.model.Testimonial(
                    id = id.value,
                    type = type.value.value,
                    title = title.value.value.trim(),
                    testimonial = testimonial.value.value.trim(),
                    userId = it.uid,
                    user = fit.asta.health.data.testimonials.model.TestimonialUser(
                        name = it.name!!,
                        role = role.value.value.trim(),
                        org = org.value.value.trim(),
                        url = it.photoUrl.toString()
                    ),
                    media = when (type.value) {
                        fit.asta.health.data.testimonials.model.TestimonialType.TEXT -> listOf()
                        fit.asta.health.data.testimonials.model.TestimonialType.IMAGE -> listOf(
                            imgBefore.value,
                            imgAfter.value
                        )

                        fit.asta.health.data.testimonials.model.TestimonialType.VIDEO -> listOf(
                            video.value
                        )
                    }
                )
            )
        }
    }

    private fun updateTestimonial(netTestimonial: fit.asta.health.data.testimonials.model.Testimonial) {
        viewModelScope.launch {
            try {
                testimonialRepo.updateTestimonial(netTestimonial).collect {
                    clearErrors()
                    _stateSubmit.value = TestimonialSubmitState.Success(it)
                }
            } catch (networkException: IOException) {
                _stateSubmit.value = TestimonialSubmitState.NetworkError(networkException)
            } catch (exception: Exception) {
                _stateSubmit.value = TestimonialSubmitState.Error(exception)
            }
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

    fun player()=player

}