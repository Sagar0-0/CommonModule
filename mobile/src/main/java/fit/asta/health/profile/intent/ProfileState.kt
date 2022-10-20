package fit.asta.health.profile.intent

import fit.asta.health.profile.model.domain.UserProfile


sealed class ProfileState {
    object Loading : ProfileState()
    class Success(val userProfile: UserProfile) : ProfileState()
    class Error(val error: Throwable) : ProfileState()
}