package fit.asta.health.scheduler.compose.screen.alarmscreen

import android.content.Context

sealed class AlarmEvent{
data class onSwipedLeft(val context: Context):AlarmEvent()
data class onSwipedRight(val context: Context):AlarmEvent()
}
