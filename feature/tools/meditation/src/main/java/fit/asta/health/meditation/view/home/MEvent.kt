package fit.asta.health.meditation.view.home

import android.content.Context

sealed class MEvent {
    data class SetTarget(val target: Float) : MEvent()
    data class SetTargetAngle(val angle: Float) : MEvent()
    data class SetDNDMode(val value: Boolean) : MEvent()
    data class Start(val context: Context) : MEvent()
    data class End(val context: Context) : MEvent()
    data class GetSheetData(val code: String) : MEvent()

}
