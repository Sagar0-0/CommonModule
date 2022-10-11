package fit.asta.health.profile_old.ui

import androidx.lifecycle.Observer

class ProfileObserver(private val viewProfile: ProfileView): Observer<ProfileAction> {
    override fun onChanged(action: ProfileAction) {

        when(action){
            is ProfileAction.LoadProfileAction -> {
                val state = ProfileView.ProfileState.LoadProfileState(action.list)
                viewProfile.changeState(state)
            }
            is ProfileAction.Error -> {
                val state = ProfileView.ProfileState.Error(action.message)
                viewProfile.changeState(state)
            }
            is ProfileAction.Empty -> {
                val state = ProfileView.ProfileState.Empty
                viewProfile.changeState(state)
            }
        }
    }
}