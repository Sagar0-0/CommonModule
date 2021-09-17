package fit.asta.health.schedule.tags.listner

import fit.asta.health.schedule.tags.data.ScheduleTagData

interface ClickListener {

    fun onClickFab()
    fun onClickSubmit()
    fun onSelectionUpdate(tagData: ScheduleTagData, updatedValue: Boolean)
}
