package fit.asta.health.profile.listener

import fit.asta.health.profile.adapter.PlainCardItem
import fit.asta.health.profile.viewmodel.ProfileViewModel

class ProfileWatcher(val viewModel: ProfileViewModel): OnChangeListener {
    override fun onTextChange(cardItem: PlainCardItem) {
        viewModel.updateEditData(cardItem)
    }
}