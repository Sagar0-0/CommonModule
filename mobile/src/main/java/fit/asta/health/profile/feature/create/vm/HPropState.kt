package fit.asta.health.profile.feature.create.vm

import fit.asta.health.profile.data.model.domain.HealthProperties


sealed class HPropState {
    object Loading : HPropState()
    object Empty : HPropState()
    object NoInternet : HPropState()
    class Success(val properties: ArrayList<HealthProperties>) : HPropState()
    class Error(val error: Throwable) : HPropState()
}