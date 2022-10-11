package fit.asta.health.profile_old.listener

import fit.asta.health.profile_old.adapter.PlainCardItem
import fit.asta.health.profile_old.viewmodel.ProfileViewModel

class ProfileWatcher(val viewModel: ProfileViewModel): OnChangeListener {
    override fun onTextChange(cardItem: PlainCardItem) {
        viewModel.updateEditData(cardItem)
    }
}