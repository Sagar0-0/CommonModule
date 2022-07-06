package fit.asta.health.common.multiselect

import fit.asta.health.common.multiselect.data.MultiSelectData

sealed class MultiSelectionAction {

    class LoadSelections(val list: List<MultiSelectData>) : MultiSelectionAction()
    object Empty : MultiSelectionAction()
    class Error(val message: String) : MultiSelectionAction()

}