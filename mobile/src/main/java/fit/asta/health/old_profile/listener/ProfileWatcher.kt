package fit.asta.health.old_profile.listener

import fit.asta.health.old_profile.adapter.PlainCardItem
import fit.asta.health.old_profile.viewmodel.ProfileViewModel

class ProfileWatcher(val viewModel: ProfileViewModel): OnChangeListener {
    override fun onTextChange(cardItem: PlainCardItem) {
        viewModel.updateEditData(cardItem)
    }
}