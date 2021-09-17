package fit.asta.health.profile.ui

import fit.asta.health.profile.adapter.ProfileItem

sealed class ProfileAction {
    class LoadProfileAction(val list: List<ProfileItem>) : ProfileAction()
    class Error(val message: String) : ProfileAction()
    object Empty : ProfileAction()
}