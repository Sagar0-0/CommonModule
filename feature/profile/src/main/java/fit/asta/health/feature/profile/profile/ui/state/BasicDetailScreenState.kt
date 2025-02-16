package fit.asta.health.feature.profile.profile.ui.state

import android.net.Uri
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.setValue
import fit.asta.health.data.profile.remote.model.BasicDetail
import fit.asta.health.data.profile.remote.model.BooleanInt
import fit.asta.health.data.profile.remote.model.Gender
import fit.asta.health.data.profile.remote.model.ProfileMedia
import kotlinx.coroutines.CoroutineScope
import java.util.Calendar

@Stable
class BasicDetailScreenState(
    val basicDetail: BasicDetail,
    val coroutineScope: CoroutineScope,
    val onEvent: (UserProfileEvent) -> Unit
) {

    var isImageCropperVisible by mutableStateOf(false)

    var userName by mutableStateOf(basicDetail.name)
        private set

    fun saveName(name: String) {
        userName = name
        onEvent(UserProfileEvent.SaveName(name))
    }

    val calendar: Calendar = Calendar.getInstance()
    var userDob by mutableStateOf(basicDetail.dob)
        private set
    private var userAge by mutableStateOf(basicDetail.age)

    fun saveDob(dob: String, age: Int) {
        userDob = dob
        userAge = age
        onEvent(UserProfileEvent.SaveDob(dob, age))
    }

    var userAgeErrorMessage by mutableStateOf<String?>(null)
        private set

    var userGender by mutableStateOf(basicDetail.gender)
        private set
    var isPregnant by mutableStateOf(basicDetail.isPregnant)
        private set
    var onPeriod by mutableStateOf(basicDetail.onPeriod)
        private set
    var userPregnancyWeek by mutableStateOf(basicDetail.pregnancyWeek)
        private set

    fun saveGender(
        gender: Gender?,
        isPregnant: BooleanInt?,
        onPeriod: BooleanInt?,
        pregnancyWeek: Int?
    ) {
        userGender = gender
        this.isPregnant = isPregnant
        this.onPeriod = onPeriod
        userPregnancyWeek = pregnancyWeek
        onEvent(
            UserProfileEvent.SaveGender(
                gender = gender,
                isPregnant = isPregnant,
                onPeriod = onPeriod,
                pregnancyWeek = pregnancyWeek
            )
        )
    }

    val email: String
        get() = basicDetail.email
    val phoneNumber: String
        get() = basicDetail.phoneNumber

    var profileImageUrl by mutableStateOf(
        if (basicDetail.media.mailUrl.isNullOrEmpty()) {
            basicDetail.media.url
        } else {
            basicDetail.media.mailUrl
        }
    )
    var profileImageLocalUri by mutableStateOf<Uri?>(null)

    private val updatedData = BasicDetail(
        userProfileAddress = basicDetail.userProfileAddress,
        dob = userDob,
        email = email,
        name = userName,
        phoneNumber = phoneNumber,
        media = ProfileMedia(
            url = basicDetail.media.url,
            mailUrl = basicDetail.media.mailUrl,
            localUri = profileImageLocalUri
        ),
        age = userAge,
        gender = userGender,
        isPregnant = isPregnant,
        onPeriod = onPeriod,
        pregnancyWeek = userPregnancyWeek,
    )

    fun clearProfile() {
        profileImageLocalUri = null
        profileImageUrl = ""
    }

    fun getUpdatedData() = updatedData
    fun saveProfileImage(croppedImage: Uri?) {
        profileImageLocalUri = croppedImage
        isImageCropperVisible = false
        onEvent(UserProfileEvent.SaveImage(profileImageLocalUri))
    }


    companion object {
        fun Saver(
            coroutineScope: CoroutineScope,
            onEvent: (UserProfileEvent) -> Unit
        ): Saver<BasicDetailScreenState, *> = listSaver(
            save = {
                listOf(
                    it.updatedData,
                    it.isImageCropperVisible,
                )
            },
            restore = {
                BasicDetailScreenState(
                    basicDetail = it[0] as BasicDetail,
                    coroutineScope,
                    onEvent
                ).apply {
                    this.isImageCropperVisible = it[1] as Boolean
                }
            }
        )
    }
}