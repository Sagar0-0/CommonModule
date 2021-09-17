package fit.asta.health.common.ui

import android.app.Activity
import android.view.View
import fit.asta.health.common.adapter.SelectionUpdateListener
import fit.asta.health.common.data.MultiSelectData

interface MultiSelectView {
    fun setContentView(activity: Activity): View?
    fun changeState(state: State)
    fun saveListener(listener: View.OnClickListener)
    fun setAdapterClickListener(listener: SelectionUpdateListener)

    sealed class State {
        class LoadSelection(val list: List<MultiSelectData>) : State()
        object Empty : State()
        class Error(val message: String) : State()
    }
}