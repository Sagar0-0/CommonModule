package fit.asta.health.feature.profile.profile.ui.state

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.setValue
import fit.asta.health.data.profile.remote.model.ProfileMedia
import fit.asta.health.data.profile.remote.model.UserDetail
import fit.asta.health.data.profile.remote.model.UserProfileAddress

@Stable
class BasicDetailScreenState(
    val userDetail: UserDetail,
    val onEvent: (UserProfileEvent) -> Unit
) {

    init {
        Log.d("INIT", ": userDetail = $userDetail")
    }

    var userName by mutableStateOf(userDetail.name)
    val email: String
        get() = userDetail.email
    private val phoneNumber: String
        get() = userDetail.phoneNumber
    private val address: String
        get() = userDetail.userProfileAddress.toString()
    var profileImageUrl by mutableStateOf(userDetail.media.mailUrl.ifEmpty { userDetail.media.url })
    var profileImageLocalUri by mutableStateOf<Uri?>(null)

    var userDob by mutableStateOf(userDetail.dob)

    private val updatedData = UserDetail(
        userProfileAddress = UserProfileAddress(
            address = address,
            country = userDetail.userProfileAddress.country,
            city = userDetail.userProfileAddress.city,
            pin = userDetail.userProfileAddress.pin,
            street = userDetail.userProfileAddress.street
        ),
        dob = userDob,
        email = email,
        name = userName,
        phoneNumber = phoneNumber,
        media = ProfileMedia(
            url = userDetail.media.url,
            mailUrl = userDetail.media.mailUrl,
            localUri = profileImageLocalUri
        )
    )

    fun clearProfile() {
        profileImageLocalUri = null
        profileImageUrl = ""
    }

    fun getUpdatedData() = updatedData

    companion object {
        fun Saver(
            onEvent: (UserProfileEvent) -> Unit
        ): Saver<BasicDetailScreenState, *> = listSaver(
            save = {
                listOf(
                    it.updatedData
                )
            },
            restore = {
                BasicDetailScreenState(
                    userDetail = it[0],
                    onEvent
                )
            }
        )
    }
}