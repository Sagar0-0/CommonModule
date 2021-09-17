package fit.asta.health.common.adapter

import fit.asta.health.common.viewmodel.MultiSelectViewModel

class SelectionUpdateListenerImpl(val viewModel: MultiSelectViewModel): SelectionUpdateListener {
    override fun onSelectionUpdate(uId: String, updatedValue: Boolean) {
       viewModel.updateData(uId, updatedValue)
    }
}