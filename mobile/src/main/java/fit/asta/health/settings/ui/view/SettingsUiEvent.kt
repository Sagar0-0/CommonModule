package fit.asta.health.settings.ui.view

sealed interface SettingsUiEvent {
    data object BACK : SettingsUiEvent
    data object ADDRESS : SettingsUiEvent
    data object REFERRAL : SettingsUiEvent
    data object WALLET : SettingsUiEvent
    data object NavigateToSubscription : SettingsUiEvent
    data object NOTIFICATION : SettingsUiEvent
    data object SHARE : SettingsUiEvent
    data object RATE : SettingsUiEvent
    data object FEEDBACK : SettingsUiEvent
    data object SIGNOUT : SettingsUiEvent
    data object DELETE : SettingsUiEvent
    data object BUG : SettingsUiEvent
    data object TERMS : SettingsUiEvent
    data object PRIVACY : SettingsUiEvent
    data object VERSION : SettingsUiEvent
    data object NavigateToAuth : SettingsUiEvent
}