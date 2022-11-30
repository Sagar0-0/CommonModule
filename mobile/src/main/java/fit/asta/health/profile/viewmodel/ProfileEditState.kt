package fit.asta.health.profile.viewmodel

import fit.asta.health.network.data.Status


sealed class ProfileEditState {
    object Loading : ProfileEditState()
    class Success(val userProfile: Status) : ProfileEditState()
    class Error(val error: Throwable) : ProfileEditState()
}