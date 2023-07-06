package fit.asta.health.tools.exercise.view.home

import android.content.Context

sealed class HomeEvent{
    data class SetLevel(val level: String) : HomeEvent()
    data class SetStyle(val style:String):HomeEvent()
    data class SetDuration(val duration:String):HomeEvent()
    data class SetBodyParts(val parts:String):HomeEvent()
    data class SetChallenge(val challenge:String):HomeEvent()
    data class SetBodyStretch(val stretch:String):HomeEvent()
    data class SetMusic(val music:String):HomeEvent()
    data class SetQuick(val quick:String):HomeEvent()
    data class SetEquipment(val equipment:String):HomeEvent()
    data class SetLanguage(val language: String) : HomeEvent()
    data class SetInstructor(val instructor: String) : HomeEvent()
    data class SetTarget(val target: Float) : HomeEvent()
    data class SetTargetAngle(val angle: Float) : HomeEvent()
    data class Start(val context: Context) : HomeEvent()
    data class End(val context: Context) : HomeEvent()
}
