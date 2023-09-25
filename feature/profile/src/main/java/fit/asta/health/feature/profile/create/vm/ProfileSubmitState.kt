package fit.asta.health.feature.profile.create.vm

import fit.asta.health.network.data.Status


sealed class ProfileSubmitState {

    object Loading : ProfileSubmitState()
    object Empty : ProfileSubmitState()
    object NoInternet : ProfileSubmitState()
    class Success(val userProfile: Status) : ProfileSubmitState()
    class Error(val error: Throwable) : ProfileSubmitState()

}