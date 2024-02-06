package fit.asta.health.feature.profile.profile.ui

sealed interface UserProfileUiEvent {
    data object LoadUserProfile : UserProfileUiEvent
    data object OnBack : UserProfileUiEvent
}
