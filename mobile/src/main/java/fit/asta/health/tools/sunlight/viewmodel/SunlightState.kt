package fit.asta.health.tools.sunlight.viewmodel

import fit.asta.health.tools.sunlight.model.network.response.ResponseData

sealed class SunlightState {
    object Loading : SunlightState()
    class Success(val sunlightTool: ResponseData.SunlightToolData) : SunlightState()
    class Error(val error: Throwable) : SunlightState()
}