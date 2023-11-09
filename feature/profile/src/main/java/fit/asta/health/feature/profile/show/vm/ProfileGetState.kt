package fit.asta.health.feature.profile.show.vm

import fit.asta.health.data.profile.remote.model.UserProfileResponse

sealed class ProfileGetState {
    object Loading : ProfileGetState()
    object Empty : ProfileGetState()
    object NoInternet : ProfileGetState()
    class Success(val userProfileResponse: UserProfileResponse) : ProfileGetState()
    class Error(val error: Throwable) : ProfileGetState()

}

sealed class ProfileCreateState {
    object Loading : ProfileCreateState()
    object Empty : ProfileCreateState()
    object NoInternet : ProfileCreateState()
    class Success(val userProfileResponse: UserProfileResponse) : ProfileCreateState()
    class Error(val error: Throwable) : ProfileCreateState()
}