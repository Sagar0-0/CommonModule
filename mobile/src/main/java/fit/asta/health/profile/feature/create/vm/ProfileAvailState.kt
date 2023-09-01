package fit.asta.health.profile.feature.create.vm


sealed class ProfileAvailState {
    object Loading : ProfileAvailState()
    class Success(val isAvailable: Boolean) : ProfileAvailState()
    class Error(val error: Throwable) : ProfileAvailState()
    object NoInternet : ProfileAvailState()

}