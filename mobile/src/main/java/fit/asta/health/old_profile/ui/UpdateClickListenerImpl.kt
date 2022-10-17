package fit.asta.health.old_profile.ui

import android.view.View
import fit.asta.health.old_profile.viewmodel.ProfileViewModel

class UpdateClickListenerImpl(val viewModel: ProfileViewModel) : View.OnClickListener {
    override fun onClick(view: View) {
        viewModel.updateProfile()
    }
}
