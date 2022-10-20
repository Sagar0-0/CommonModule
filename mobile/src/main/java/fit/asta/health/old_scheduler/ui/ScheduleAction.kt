package fit.asta.health.old_scheduler.ui

import fit.asta.health.old_scheduler.data.ScheduleData
import fit.asta.health.old_scheduler.tags.data.ScheduleTagData


sealed class ScheduleAction {
    class LoadSchedule(val schedule: ScheduleData) : ScheduleAction()
    class LoadTag(val tag: ScheduleTagData?) : ScheduleAction()
    class Error(val message: String) : ScheduleAction()
    object Empty : ScheduleAction()
}