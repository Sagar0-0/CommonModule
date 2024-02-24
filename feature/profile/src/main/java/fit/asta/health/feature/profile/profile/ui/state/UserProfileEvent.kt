package fit.asta.health.feature.profile.profile.ui.state

import fit.asta.health.data.profile.remote.model.BooleanInt
import fit.asta.health.data.profile.remote.model.Gender

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

    data class SaveHeight(val height: Float, val unit: Int) : UserProfileEvent
    data class SaveWeight(val weight: Float, val unit: Int) : UserProfileEvent
}