package fit.asta.health.sunlight.remote.model

import kotlinx.coroutines.flow.Flow


data class SunDurationState(
    var onPause:Boolean?=false,
    val onResume:SunDurationProgress?=null,
    var onStop:Boolean?=false,
    val update:SunDurationProgress?=null
)

data class SunDurationProgress (
    var current:Long?=null,
    var target:Long?=null,
    var vitaminDConsumed:String?=null,
    var vitDTarget:String?=null,
    var startTime:Long?=null,
    var endTime:Long?=null,
    var progress:Float?=null
    )