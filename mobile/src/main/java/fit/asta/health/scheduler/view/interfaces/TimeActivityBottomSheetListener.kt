package fit.asta.health.scheduler.view.interfaces

import fit.asta.health.scheduler.model.net.scheduler.Rep

interface TimeActivityBottomSheetListener {
    fun setSnoozeTime(time: Int)

    fun setAdvancedReminderTime(time: Int)

    fun setDurationTime(time: Int)

    fun setRepeatTime(repeatItem: Rep)
}