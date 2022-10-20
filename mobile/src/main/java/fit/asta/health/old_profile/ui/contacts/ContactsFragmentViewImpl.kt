package fit.asta.health.old_profile.ui.contacts

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fit.asta.health.R
import fit.asta.health.old_profile.adapter.viewholders.ProfileTabType
import fit.asta.health.old_profile.listener.OnChangeListener
import fit.asta.health.old_profile.listener.OnChipActionListener
import fit.asta.health.old_profile.ui.ProfileView

class ContactsFragmentViewImpl: ContactsFragmentView {

    private var rootView: View? = null
    override fun setContentView(activity: Activity, container: ViewGroup?): View? {
        rootView = LayoutInflater.from(activity).inflate(
                R.layout.profile_contacts_fragment, container,
                false)
        intializeViews()
        return rootView
    }

    override fun changeState(state: ProfileView.ProfileState) {
        when(state){
            is ProfileView.ProfileState.LoadProfileState -> {
                state.list
            }
            else -> { }
        }
    }

    override fun setProfileTabType(tabType: ProfileTabType) {
        TODO("Not yet implemented")
    }

    override fun setAdapterListener(listener: OnChangeListener) {
        TODO("Not yet implemented")
    }

    override fun setOnChipListener(listener: OnChipActionListener) {
        TODO("Not yet implemented")
    }

    private fun intializeViews(){

        rootView?.let {
        }
    }
}