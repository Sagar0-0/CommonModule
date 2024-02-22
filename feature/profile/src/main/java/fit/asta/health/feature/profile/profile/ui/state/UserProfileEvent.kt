package fit.asta.health.feature.profile.profile.ui.state

import fit.asta.health.data.profile.remote.model.UserProfileResponse

sealed interface UserProfileEvent {
    data object ResetHealthProperties : UserProfileEvent
    data object NavigateToOrders : UserProfileEvent
    data object NavigateToWallet : UserProfileEvent
    data object NavigateToSubscription : UserProfileEvent

    data class UpdateUserProfileData(val userProfileResponse: UserProfileResponse) :
        UserProfileEvent

    data class GetHealthProperties(val id: String) : UserProfileEvent

    data class SaveUserName(val userName: String) : UserProfileEvent
}