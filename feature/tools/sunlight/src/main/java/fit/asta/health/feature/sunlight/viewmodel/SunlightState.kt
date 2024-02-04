package fit.asta.health.feature.sunlight.viewmodel

import fit.asta.health.data.sunlight.model.network.response.SunlightToolData

sealed class SunlightState {
    data object Loading : SunlightState()
    class Success(val sunlightTool: SunlightToolData) : SunlightState()
    class Error(val error: Throwable) : SunlightState()
}