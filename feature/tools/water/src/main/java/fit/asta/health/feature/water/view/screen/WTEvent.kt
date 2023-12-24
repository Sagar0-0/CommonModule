package fit.asta.health.feature.water.view.screen

import android.content.Context

sealed class WTEvent {
    data class SelectTarget(val target: Float) : WTEvent()
    data class SelectAngle(val angle: Float) : WTEvent()
    data class SelectBeverage(val index: String) : WTEvent()
    data class SelectContainer(val index: Int) : WTEvent()
    data class SheetState(val state: Boolean) : WTEvent()
    data class DialogState(val state: Boolean) : WTEvent()
    data class Start(val context: Context) : WTEvent()
    data object UpdateQuantity : WTEvent()
    data object Schedule : WTEvent()
}