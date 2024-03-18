package fit.asta.health.feature.water.view.screen

import android.content.Context

sealed class WTEvent {
    data class Start(val context: Context) : WTEvent()
    data class UpdateBevDetails(val title : String,val quantity : Int) : WTEvent()
    data class DeleteRecentConsumption(val bevName : String) : WTEvent()
    data class GoalChange(val goal: Int) : WTEvent()
    data class UpdateOnSliderChangeQuantity(val bevTitle : String,val sliderValue : Float) : WTEvent()
    data class UndoConsumption(val bevName : String) : WTEvent()
    data object UpdateBevQuantity : WTEvent()
    data object ConsumptionDetails : WTEvent()
    data object OnDisposeAddData : WTEvent()
    data object RetrySection : WTEvent()

}