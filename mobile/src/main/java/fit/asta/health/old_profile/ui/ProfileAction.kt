package fit.asta.health.old_profile.ui

import fit.asta.health.old_profile.adapter.ProfileItem

sealed class ProfileAction {
    class LoadProfileAction(val list: List<ProfileItem>) : ProfileAction()
    class Error(val message: String) : ProfileAction()
    object Empty : ProfileAction()
}