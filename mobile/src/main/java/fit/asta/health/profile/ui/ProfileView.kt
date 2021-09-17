package fit.asta.health.profile.ui

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import fit.asta.health.profile.adapter.ProfileItem
import fit.asta.health.profile.adapter.viewholders.ProfileTabType
import fit.asta.health.profile.listener.OnChangeListener
import fit.asta.health.profile.listener.OnChipActionListener

interface ProfileView {

    fun setContentView(activity: Activity, container: ViewGroup?): View?
    fun changeState(state: ProfileState)
    fun setProfileTabType(tabType: ProfileTabType)
    fun setAdapterListener(listener: OnChangeListener)
    fun setOnChipListener(listener: OnChipActionListener)

    sealed class ProfileState {
        class LoadProfileState(val list: List<ProfileItem>) : ProfileState()
        class Error(val message: String) : ProfileState()
        object Empty: ProfileState()
    }
}