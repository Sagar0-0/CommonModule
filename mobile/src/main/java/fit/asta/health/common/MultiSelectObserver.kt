package fit.asta.health.common

import androidx.lifecycle.Observer
import fit.asta.health.common.ui.MultiSelectView

class MultiSelectObserver(val view: MultiSelectView) : Observer<MultiSelectionAction> {
    override fun onChanged(action: MultiSelectionAction) {
        when (action) {
            is MultiSelectionAction.LoadSelections -> {
                val state = MultiSelectView.State.LoadSelection(action.list)
                view.changeState(state)
            }
            MultiSelectionAction.Empty -> {
                val state = MultiSelectView.State.Empty
                view.changeState(state)
            }

            is MultiSelectionAction.Error -> {
                val state = MultiSelectView.State.Error(action.message)
                view.changeState(state)
            }
        }

    }
}