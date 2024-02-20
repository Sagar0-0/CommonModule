package fit.asta.health.feature.water.view.screen

import android.content.Context
import androidx.compose.ui.graphics.Color

sealed class WTEvent {
    data class SelectTarget(val target: Float) : WTEvent()
    data class SelectAngle(val angle: Float) : WTEvent()
    data class SelectBeverage(val index: String) : WTEvent()
    data class SelectContainer(val index: Int) : WTEvent()
    data class SheetState(val state: Boolean) : WTEvent()
    data class DialogState(val state: Boolean) : WTEvent()
    data class Start(val context: Context) : WTEvent()
    data class UpdateBevDetails(val title : String,val quantity : Int) : WTEvent()

    data class GoalChange(val goal: Int) : WTEvent()
    data class colorChange(val color : Color) : WTEvent()
    data class UpdateOnSliderChangeQuantity(val bevTitle : String,val sliderValue : Float) : WTEvent()
    data object UpdateQuantity : WTEvent()
    data object Schedule : WTEvent()
    data object UpdateBevQuantity : WTEvent()

    data object RetrySection : WTEvent()
}