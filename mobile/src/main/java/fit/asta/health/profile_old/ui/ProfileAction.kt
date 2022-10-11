package fit.asta.health.profile_old.ui

import fit.asta.health.profile_old.adapter.ProfileItem

sealed class ProfileAction {
    class LoadProfileAction(val list: List<ProfileItem>) : ProfileAction()
    class Error(val message: String) : ProfileAction()
    object Empty : ProfileAction()
}