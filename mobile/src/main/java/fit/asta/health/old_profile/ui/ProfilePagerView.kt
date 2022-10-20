package fit.asta.health.old_profile.ui

import android.app.Activity
import android.view.View

interface ProfilePagerView {

    fun setContentView(activity: Activity): View?
    fun changeState(state: ProfileView.ProfileState)
    fun setUpViewPager(fragmentActivity: ProfileActivity, listener: ProfileViewPagerListener)
    fun updateClickListener(listener: View.OnClickListener)

}