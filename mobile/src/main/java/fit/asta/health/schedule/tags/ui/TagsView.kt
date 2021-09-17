package fit.asta.health.schedule.tags.ui

import android.app.Activity
import android.view.View
import fit.asta.health.schedule.tags.data.ScheduleTagData
import fit.asta.health.schedule.tags.listner.ClickListener

interface TagsView {

    fun setContentView(activity: Activity): View?
    fun changeState(state: State)
    fun fabClickListener(listener: ClickListener)
    fun submitClickListener(listener: ClickListener)
    fun setAdapterClickListener(listener: ClickListener)

    sealed class State {
        class LoadTag(val tag: ScheduleTagData) : State()
        class LoadTagList(val list: List<ScheduleTagData>) : State()
        object Empty : State()
        class Error(val message: String) : State()
    }
}