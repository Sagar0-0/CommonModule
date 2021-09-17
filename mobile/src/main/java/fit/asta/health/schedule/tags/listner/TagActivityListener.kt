package fit.asta.health.schedule.tags.listner

import fit.asta.health.schedule.tags.data.ScheduleTagData

interface TagActivityListener {
    fun onSelectionResult(selectedData: ScheduleTagData)
}
