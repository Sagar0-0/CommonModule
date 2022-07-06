package fit.asta.health.common.multiselect.adapter

import fit.asta.health.common.multiselect.viewmodel.MultiSelectViewModel

class SelectionUpdateListenerImpl(val viewModel: MultiSelectViewModel): SelectionUpdateListener {
    override fun onSelectionUpdate(uId: String, updatedValue: Boolean) {
       viewModel.updateData(uId, updatedValue)
    }
}