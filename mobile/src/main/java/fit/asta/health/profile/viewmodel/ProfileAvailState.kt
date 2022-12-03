package fit.asta.health.profile.viewmodel

import fit.asta.health.profile.model.network.UserProfileAvailable


sealed class ProfileAvailState {
    object Loading : ProfileAvailState()
    class Success(val userProfile: UserProfileAvailable) : ProfileAvailState()
    class Error(val error: Throwable) : ProfileAvailState()
}