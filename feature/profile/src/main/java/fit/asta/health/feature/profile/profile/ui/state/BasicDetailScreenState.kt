package fit.asta.health.feature.profile.profile.ui.state

import android.net.Uri
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.setValue
import fit.asta.health.data.profile.remote.model.BasicDetail
import fit.asta.health.data.profile.remote.model.ProfileMedia
import fit.asta.health.data.profile.remote.model.UserProfileAddress
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.Calendar

@Stable
class BasicDetailScreenState(
    val basicDetail: BasicDetail,
    private val scope: CoroutineScope,
    val onEvent: (UserProfileEvent) -> Unit
) {

    var userName by mutableStateOf(basicDetail.name)

    fun saveName(name: String) {
        onEvent(UserProfileEvent.SaveUserName(name))
    }

    val calendar: Calendar = Calendar.getInstance()
    var userAge by mutableStateOf(basicDetail.age)
        private set
    var userAgeErrorMessage by mutableStateOf<String?>(null)
        private set

    fun setAge(value: Int) {
        userAgeErrorMessage = if (value == 0) {
            "Invalid age"
        } else if (value < 10) {
            "Age should be more than 10"
        } else {
            null
        }
        userAge = value
    }

    var userGender by mutableStateOf(basicDetail.gender)
    var isPregnant by mutableStateOf(basicDetail.isPregnant)
    var onPeriod by mutableStateOf(basicDetail.onPeriod)
    var userPregnancyWeek by mutableStateOf(basicDetail.pregnancyWeek?.toString())
    var userPregnancyWeekErrorMessage by mutableStateOf<String?>(null)

    val email: String
        get() = basicDetail.email
    private val phoneNumber: String
        get() = basicDetail.phoneNumber
    private val address: String
        get() = basicDetail.userProfileAddress.toString()
    var profileImageUrl by mutableStateOf(basicDetail.media.mailUrl.ifEmpty { basicDetail.media.url })
    var profileImageLocalUri by mutableStateOf<Uri?>(null)

    var userDob by mutableStateOf(basicDetail.dob)

    private val updatedData = BasicDetail(
        userProfileAddress = UserProfileAddress(
            address = address,
            country = basicDetail.userProfileAddress.country,
            city = basicDetail.userProfileAddress.city,
            pin = basicDetail.userProfileAddress.pin,
            street = basicDetail.userProfileAddress.street
        ),
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
        pregnancyWeek = userPregnancyWeek?.toIntOrNull(),
    )

    fun clearProfile() {
        profileImageLocalUri = null
        profileImageUrl = ""
    }

    fun getUpdatedData() = updatedData

    @OptIn(ExperimentalMaterial3Api::class)
    fun openNameSheet(bottomSheetState: SheetState, bottomSheetVisible: MutableState<Boolean>) {
        bottomSheetVisible.value = true
        scope.launch {
            bottomSheetState.expand()
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    fun closeNameSheet(bottomSheetState: SheetState, bottomSheetVisible: MutableState<Boolean>) {
        bottomSheetVisible.value = false
        scope.launch {
            bottomSheetState.hide()
        }
    }

    companion object {
        fun Saver(
            scope: CoroutineScope,
            onEvent: (UserProfileEvent) -> Unit
        ): Saver<BasicDetailScreenState, *> = listSaver(
            save = {
                listOf(
                    it.updatedData
                )
            },
            restore = {
                BasicDetailScreenState(
                    basicDetail = it[0],
                    scope,
                    onEvent
                )
            }
        )
    }
}