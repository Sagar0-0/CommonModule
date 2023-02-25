package fit.asta.health.old.scheduler.tags.listner

import fit.asta.health.old.scheduler.tags.data.ScheduleTagData

interface TagActivityListener {
    fun onSelectionResult(selectedData: ScheduleTagData)
}
