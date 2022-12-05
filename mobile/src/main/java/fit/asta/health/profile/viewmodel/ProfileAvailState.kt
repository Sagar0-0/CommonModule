package fit.asta.health.profile.viewmodel


sealed class ProfileAvailState {
    object Loading : ProfileAvailState()
    class Success(val isAvailable: Boolean) : ProfileAvailState()
    class Error(val error: Throwable) : ProfileAvailState()
}