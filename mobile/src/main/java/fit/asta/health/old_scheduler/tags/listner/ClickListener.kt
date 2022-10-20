package fit.asta.health.old_scheduler.tags.listner

import fit.asta.health.old_scheduler.tags.data.ScheduleTagData

interface ClickListener {

    fun onClickFab()
    fun onClickSubmit()
    fun onSelectionUpdate(tagData: ScheduleTagData, updatedValue: Boolean)
}
