package fit.asta.health.profile.ui

import android.os.Bundle
import android.view.MotionEvent
import androidx.fragment.app.FragmentActivity
import fit.asta.health.profile.viewmodel.ProfileViewModel
import fit.asta.health.utils.hideKeyboardFrom
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class ProfileActivity : FragmentActivity() {

    private val profilePagerView: ProfilePagerView by inject()
    private val profileViewModel: ProfileViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(profilePagerView.setContentView(this))
        profilePagerView.setUpViewPager(this, ProfileViewPagerListener(profileViewModel))
        profileViewModel.getProfileData()
        profilePagerView.updateClickListener(UpdateClickListenerImpl(profileViewModel))
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {

        if (currentFocus != null) {

            hideKeyboardFrom(currentFocus!!)
        }

        return super.dispatchTouchEvent(ev)
    }

}
