package fit.asta.health.profile.ui

import android.view.View
import fit.asta.health.profile.viewmodel.ProfileViewModel

class UpdateClickListenerImpl(val viewModel: ProfileViewModel) : View.OnClickListener {
    override fun onClick(view: View) {
        viewModel.updateProfile()
    }
}
