package fit.asta.health.old_scheduler.tags.listner

import fit.asta.health.old_scheduler.tags.data.ScheduleTagData

interface TagActivityListener {
    fun onSelectionResult(selectedData: ScheduleTagData)
}
