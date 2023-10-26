package fit.asta.health.feature.settings.view

sealed interface SettingsUiEvent {
    data class SetTheme(val theme: String) : SettingsUiEvent
    data object BACK : SettingsUiEvent
    data object Orders : SettingsUiEvent
    data object ADDRESS : SettingsUiEvent
    data object REFERRAL : SettingsUiEvent
    data object WALLET : SettingsUiEvent
    data object NavigateToSubscription : SettingsUiEvent
    data object NOTIFICATION : SettingsUiEvent
    data object SHARE : SettingsUiEvent
    data object SIGNOUT : SettingsUiEvent
    data object DELETE : SettingsUiEvent
    data object BUG : SettingsUiEvent
    data object TERMS : SettingsUiEvent
    data object PRIVACY : SettingsUiEvent
    data object VERSION : SettingsUiEvent
    data object NavigateToAuthScreen : SettingsUiEvent
    data object NavigateToOrders : SettingsUiEvent
}