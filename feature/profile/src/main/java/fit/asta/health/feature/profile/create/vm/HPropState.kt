package fit.asta.health.feature.profile.create.vm

import fit.asta.health.data.profile.remote.model.HealthProperties


sealed class HPropState {
    data object Loading : HPropState()
    data object Empty : HPropState()
    data object NoInternet : HPropState()
    class Success(val properties: ArrayList<HealthProperties>) : HPropState()
    class Error(val error: Throwable) : HPropState()
}