package fit.asta.health.old_scheduler.ui

import androidx.lifecycle.Observer

class ScheduleObserver(private val viewSchedule: ScheduleView) :
    Observer<ScheduleAction> {
    override fun onChanged(action: ScheduleAction) {

        when (action) {
            is ScheduleAction.LoadSchedule -> {
                val state = ScheduleView.State.LoadScheduleAction(action.schedule)
                viewSchedule.changeStateSchedule(state)
            }
            is ScheduleAction.LoadTag -> {
                val state = ScheduleView.State.LoadTagAction(action.tag)
                viewSchedule.changeStateSchedule(state)
            }
            is ScheduleAction.Error -> {
                val state = ScheduleView.State.Error(action.message)
                viewSchedule.changeStateSchedule(state)
            }
            is ScheduleAction.Empty -> {
                val state = ScheduleView.State.Empty
                viewSchedule.changeStateSchedule(state)
            }
        }
    }
}