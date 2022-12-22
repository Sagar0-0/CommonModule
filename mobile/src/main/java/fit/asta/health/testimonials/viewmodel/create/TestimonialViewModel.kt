package fit.asta.health.testimonials.viewmodel.create

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.R
import fit.asta.health.firebase.model.AuthRepo
import fit.asta.health.network.NetworkHelper
import fit.asta.health.network.data.SingleFileUpload
import fit.asta.health.network.data.UploadInfo
import fit.asta.health.network.repo.FileUploadRepo
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
    val networkHelper: NetworkHelper,
    private val fileRepo: FileUploadRepo,
) : ViewModel() {

    var title by mutableStateOf(UiString.Resource(R.string.testimonial_title_create))
        private set

    var data by mutableStateOf(TestimonialData())
        private set

    var media by mutableStateOf(SingleFileUpload())
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
            testimonialRepo.getTestimonial(userId).catch { exception ->
                //_mutableState.value = TestimonialGetState.Error(exception)
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
                    org = it.user.org
                )
                _mutableState.value = TestimonialGetState.Success(it)
            }
        }
    }

    private fun updateTestimonial(netTestimonial: Testimonial) {
        viewModelScope.launch {
            testimonialRepo.updateTestimonial(netTestimonial).catch { exception ->
                _stateChannel.send(TestimonialSubmitState.Error(exception))
            }.collect {
                clearErrors()
                _stateChannel.send(TestimonialSubmitState.Success(it))
            }
        }
    }

    private fun uploadFile(filePath: Uri?) {

        if (filePath == null) return

        viewModelScope.launch {
            authRepo.getUser()?.let { user ->
                fileRepo.uploadFile(
                    fileInfo = UploadInfo(
                        id = media.id,
                        uid = user.uid,
                        feature = "testimonial",
                        filePath = filePath
                    )
                ).catch {

                }.collect {
                    media = it
                }
            }
        }
    }

    fun onEvent(event: TestimonialEvent) {
        when (event) {
            is TestimonialEvent.OnTypeChange -> {
                if (event.type == TestimonialType.IMAGE) {
                    if (data.media.isEmpty()) {
                        data.media.add(0, Media(inx = 0, title = "Before Image"))
                        data.media.add(1, Media(inx = 1, title = "After Image"))
                    }
                }
                this.data = this.data.copy(type = event.type)
                this.data.enableSubmit = validateTestimonial(event.type)
            }
            is TestimonialEvent.OnTitleChange -> {
                this.data = this.data.copy(title = event.title)
                this.data.titleError = onValidateText(event.title, 4, 64)
                this.data.enableSubmit = validateTestimonial(data.type)
            }
            is TestimonialEvent.OnTestimonialChange -> {
                this.data = this.data.copy(testimonial = event.testimonial)
                this.data.testimonialError = onValidateText(event.testimonial, 32, 256)
                this.data.enableSubmit = validateTestimonial(data.type)
            }
            is TestimonialEvent.OnOrgChange -> {
                this.data = this.data.copy(org = event.org)
                this.data.orgError = onValidateText(event.org, 4, 32)
                this.data.enableSubmit = validateTestimonial(data.type)
            }
            is TestimonialEvent.OnRoleChange -> {
                this.data = this.data.copy(role = event.role)
                this.data.roleError = onValidateText(event.role, 2, 32)
                this.data.enableSubmit = validateTestimonial(data.type)
            }
            is TestimonialEvent.OnMediaClear -> {
                this.data.media[event.inx] = this.data.media[event.inx].copy(url = "")
            }
            is TestimonialEvent.OnSubmit -> {
                submit()
            }
            is TestimonialEvent.OnMediaIndex -> {
                curInx = event.inx
            }
            is TestimonialEvent.OnMediaSelect -> {
                this.data.media[curInx] = this.data.media[curInx].copy(url = event.url.toString())
                uploadFile(event.url)
            }
        }
    }

    private fun submit() {

        //TODO - Error Handling and Validation required??

        authRepo.getUser()?.let {
            updateTestimonial(
                Testimonial(
                    id = this.data.id,
                    type = this.data.type,
                    title = this.data.title.trim(),
                    testimonial = this.data.testimonial.trim(),
                    userId = it.uid,
                    user = TestimonialUser(
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

/*
Testimonials:

-> 1. Upload file to S3 -> 2. Create the entry in the DB -> 3. Response
-> 1. Upload file to S3 -> 2. Create the entry in the DB -> 3. Response

Cache in the Local DB

-> 4. Read the URL from response -> 5. Update the Urls in the Testimonial -> 6. Create/Update Testimonial

Profile:

-> 1. Upload file to S3 -> 2. Create the entry in the DB -> 3. Response

Cache in the Local DB

-> 4. Read the URL from response -> 5. Update the Urls in the Profile -> 6. Create/Update Profile

Schedule Tag:

-> 1. Upload file to S3 -> 2. Create the entry in the DB -> 3. Response

Cache in the Local DB

-> 4. Read the URL from response -> 5. Update the Urls in the Tag -> 6. Create/Update Tag


Feedback:

-> 1. Upload file to S3 -> 2. Create the Cache in the DB -> 3. Response

Cache in the Local DB

-> 4. Read the URL from response -> 5. Update the Urls in the Feedback -> 6. Create/Update Feedback


Order Madicine (Prescription):


Consultation Chat (Prescriptions and Reports):


Review (Images or Videos):


Diagnostics (Prescription):



Server Cache:

Create / Update:

-> 1. Upload file to S3 -> 2. Create the Cache in the DB -> 3. Response

-> 4. Create/Update Testimonial request -> 5. Read the media information from Cache -> 6. Change the model data -> 7. Create/Update the db entry -> 8. Delete the cache

Read:

1. Find the testimonial  -> 2. If the testimonial is avaiable, read the testimonial
    -> 3. If the testimonial not availble then find the media in the cache -> 4. If media available read it from the Cache -> 5. Create the empty testimonial with Media
    -> 6. Response
    -> 4. If the media not available the send empty or not found response

Who need to Cache??

App ->

DisAdv:
1.Is Room DB can be tampered? 2. iOS and Android implementation
Adv: 1. Less burden on the server, 2. Offline support can be added

Server ->

DisAdv: 1. Usage of more computation power
Adv: 1. Security 2. Single implementation
 */