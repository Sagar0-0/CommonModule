package fit.asta.health.tools.sunlight.viewmodel

import fit.asta.health.tools.sunlight.model.domain.SunlightTool

sealed class SunlightState {
    object Loading : SunlightState()
    class Success(val sunlightTool: SunlightTool) : SunlightState()
    class Error(val error: Throwable) : SunlightState()
}