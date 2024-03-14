package fit.asta.health.feature.profile.profile.ui.state

import android.net.Uri
import fit.asta.health.data.profile.remote.model.BooleanInt
import fit.asta.health.data.profile.remote.model.Gender
import fit.asta.health.data.profile.remote.model.TimeSchedule
import fit.asta.health.data.profile.remote.model.UserProperties

sealed interface UserProfileEvent {
    data object ResetHealthProperties : UserProfileEvent
    data object NavigateToOrders : UserProfileEvent
    data object NavigateToWallet : UserProfileEvent
    data object NavigateToSubscription : UserProfileEvent
    data class GetHealthProperties(val id: String) : UserProfileEvent
    data class SaveName(val userName: String) : UserProfileEvent
    data class SaveDob(val dob: String, val age: Int) : UserProfileEvent
    data class SaveGender(
        val gender: Gender?,
        val isPregnant: BooleanInt?,
        val onPeriod: BooleanInt?,
        val pregnancyWeek: Int?
    ) : UserProfileEvent

    data class SaveHeight(val height: Double, val unit: Int) : UserProfileEvent
    data class SaveWeight(val weight: Double, val unit: Int) : UserProfileEvent
    data class SavePropertiesList(
        val screenName: String,
        val fieldName: String,
        val list: List<UserProperties>
    ) : UserProfileEvent

    data class SaveImage(val profileImageLocalUri: Uri?) : UserProfileEvent
    data class SaveTimeSchedule(
        val screenName: String,
        val fieldName: String,
        val timeSchedule: TimeSchedule
    ) : UserProfileEvent

    data class SaveBodyType(val value: Int) : UserProfileEvent

    data object NavigateToReferral : UserProfileEvent
}