package fit.asta.health.feature.profile.create.vm


sealed class ProfileAvailState {
    data object Loading : ProfileAvailState()
    class Success(val isAvailable: Boolean) : ProfileAvailState()
    class Error(val error: Throwable) : ProfileAvailState()
    data object NoInternet : ProfileAvailState()

}