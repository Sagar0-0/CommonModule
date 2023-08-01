package fit.asta.health.settings.data

sealed class SettingsUiEvent {
    object BACK : SettingsUiEvent()
    object REFERRAL : SettingsUiEvent()
    object WALLET : SettingsUiEvent()
    object SUBSCRIBE : SettingsUiEvent()
    object NOTIFICATION : SettingsUiEvent()
    object SHARE : SettingsUiEvent()
    object RATE : SettingsUiEvent()
    object FEEDBACK : SettingsUiEvent()
    object SIGNOUT : SettingsUiEvent()
    object DELETE : SettingsUiEvent()
    object BUG : SettingsUiEvent()
    object TERMS : SettingsUiEvent()
    object PRIVACY : SettingsUiEvent()
    object VERSION : SettingsUiEvent()
}