package fit.asta.health.sunlight.feature.screens.home.homeScreen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import fit.asta.health.common.utils.Prc
import fit.asta.health.sunlight.remote.model.SunlightHomeData
import fit.asta.health.sunlight.remote.model.SunlightSessionData
import fit.asta.health.sunlight.remote.model.Sup

data class SunlightHomeState(
    val isLoading: Boolean = false,
    val sunlightHomeResponse: SunlightHomeData? = null,
    val sunlightSessionData: SunlightSessionData? = null,
    val isTimerRunning: MutableState<Boolean> = mutableStateOf(false),
    val skinConditionData: SnapshotStateList<Prc> = mutableStateListOf(),
    val supplementData: MutableState<Sup?> = mutableStateOf(null),
    val sunlightProgress: MutableState<String> = mutableStateOf(""),
    val remainingTime: MutableState<Float> = mutableFloatStateOf(0f),
    val totalTime: MutableState<Long> = mutableLongStateOf(0),
    val totalDConsumed: MutableState<String> = mutableStateOf("0"),
    val isTimerPaused: MutableState<Boolean> = mutableStateOf(false),
    val error: String? = null
)