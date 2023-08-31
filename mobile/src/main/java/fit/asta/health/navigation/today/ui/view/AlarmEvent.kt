package fit.asta.health.navigation.today.ui.view

import android.content.Context
import fit.asta.health.data.scheduler.db.entity.AlarmEntity

sealed class AlarmEvent {
    data class SetAlarmState(val state: Boolean, val alarm: AlarmEntity, val context: Context) :
        AlarmEvent()

    data object OnBack : AlarmEvent()
}
