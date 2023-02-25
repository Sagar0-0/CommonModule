package fit.asta.health.tools.water.viewmodel

sealed class WaterState {
    object Loading : WaterState()
    object Success : WaterState()
    class Error(val error: Throwable) : WaterState()
    //Used only inside the success tool
    object Modified : WaterState()
}