package fit.asta.health.schedule.ui

import fit.asta.health.schedule.data.ScheduleData
import fit.asta.health.schedule.tags.data.ScheduleTagData


sealed class ScheduleAction {
    class LoadSchedule(val schedule: ScheduleData) : ScheduleAction()
    class LoadTag(val tag: ScheduleTagData?) : ScheduleAction()
    class Error(val message: String) : ScheduleAction()
    object Empty : ScheduleAction()
}