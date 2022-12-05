package fit.asta.health.tools.water.viewmodel

import fit.asta.health.tools.water.model.domain.WaterTool

sealed class WaterState {
    object Loading : WaterState()
    object Success : WaterState()
    class Error(val error: Throwable) : WaterState()

    //Used only inside the success tool
    object Modified : WaterState()
}