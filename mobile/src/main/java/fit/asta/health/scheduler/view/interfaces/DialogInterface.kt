package fit.asta.health.scheduler.view.interfaces

import fit.asta.health.scheduler.model.net.scheduler.Stat

interface DialogInterface {
    fun sendVariantIntervalData(alarmTimeItem: Stat)

    fun deleteVariantIntervalData(alarmTimeItem: Stat)
}