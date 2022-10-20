package fit.asta.health.old_scheduler.ui

import android.app.Activity
import android.view.View
import fit.asta.health.old_scheduler.data.ScheduleData
import fit.asta.health.old_scheduler.data.ScheduleTimeData
import fit.asta.health.old_scheduler.tags.data.ScheduleTagData

interface ScheduleView {

    fun setContentView(activity: Activity): View?
    fun changeStateSchedule(state: State)
    fun captureTime(callBack: (time: ScheduleTimeData) -> Unit)
    fun submitClickListener(listener: View.OnClickListener)

    sealed class State {
        class LoadScheduleAction(val schedule: ScheduleData) : State()
        class LoadTagAction(val tag: ScheduleTagData?) : State()
        class Error(val message: String) : State()
        object Empty : State()
    }
}