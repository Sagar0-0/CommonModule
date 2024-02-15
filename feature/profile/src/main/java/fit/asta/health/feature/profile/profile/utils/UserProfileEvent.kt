package fit.asta.health.feature.profile.profile.utils

import fit.asta.health.data.profile.remote.model.UserProfileResponse

sealed interface UserProfileEvent {
    data class UpdateUserProfileData(val userProfileResponse: UserProfileResponse) :
        UserProfileEvent
}