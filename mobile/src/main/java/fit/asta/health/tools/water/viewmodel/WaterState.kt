package fit.asta.health.tools.water.viewmodel

import fit.asta.health.tools.water.model.domain.WaterTool

sealed class WaterState {
    object Loading : WaterState()
    class Success(val waterTool: WaterTool) : WaterState()
    class Error(val error: Throwable) : WaterState()
}