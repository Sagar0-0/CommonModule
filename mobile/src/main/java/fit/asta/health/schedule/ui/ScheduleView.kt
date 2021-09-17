package fit.asta.health.schedule.ui

import android.app.Activity
import android.view.View
import fit.asta.health.schedule.data.ScheduleData
import fit.asta.health.schedule.data.ScheduleTimeData
import fit.asta.health.schedule.tags.data.ScheduleTagData

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