package fit.asta.health.profile.viewmodel

sealed class ProfileEvent {
    data class GetHealthProperties(val propertyType: String) : ProfileEvent()
}
