package fit.asta.health.profile.viewmodel

import fit.asta.health.profile.model.domain.UserProfile


sealed class ProfileState {
    object Loading : ProfileState()
    object Empty : ProfileState()
    object NoInternet : ProfileState()
    class Success(val userProfile: UserProfile) : ProfileState()
    class Error(val error: Throwable) : ProfileState()

}