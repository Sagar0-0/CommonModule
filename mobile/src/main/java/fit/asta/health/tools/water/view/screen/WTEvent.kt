package fit.asta.health.tools.water.view.screen

import android.content.Context

sealed class WTEvent {
    data class SelectTarget(val target: Float) : WTEvent()
    data class SelectAngle(val angle: Float) : WTEvent()
    data class SelectBeverage(val Index: String) : WTEvent()
    data class SelectContainer(val Index: Int) : WTEvent()
    data class SheetState(val state: Boolean) : WTEvent()
    data class DialogState(val state: Boolean) : WTEvent()
    data class Start(val context: Context) : WTEvent()
    object UpdateQuantity : WTEvent()
    object Schedule : WTEvent()
}