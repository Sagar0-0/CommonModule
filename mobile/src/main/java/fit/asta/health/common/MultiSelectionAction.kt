package fit.asta.health.common

import fit.asta.health.common.data.MultiSelectData

sealed class MultiSelectionAction {

    class LoadSelections(val list: List<MultiSelectData>) : MultiSelectionAction()
    object Empty : MultiSelectionAction()
    class Error(val message: String) : MultiSelectionAction()

}