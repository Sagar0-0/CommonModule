package fit.asta.health.profile.viewmodel

import fit.asta.health.profile.model.domain.HealthProperties


sealed class HPropState {
    object Loading : HPropState()
    class Success(val properties: List<HealthProperties>) : HPropState()
    class Error(val error: Throwable) : HPropState()
}