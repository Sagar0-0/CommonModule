package fit.asta.health.profile.feature.show.vm

import fit.asta.health.profile.data.model.domain.UserProfile


sealed class ProfileGetState {
    object Loading : ProfileGetState()
    object Empty : ProfileGetState()
    object NoInternet : ProfileGetState()
    class Success(val userProfile: UserProfile) : ProfileGetState()
    class Error(val error: Throwable) : ProfileGetState()

}


sealed class ProfileCreateState {
    object Loading : ProfileCreateState()
    object Empty : ProfileCreateState()
    object NoInternet : ProfileCreateState()
    class Success(val userProfile: UserProfile) : ProfileCreateState()
    class Error(val error: Throwable) : ProfileCreateState()
}