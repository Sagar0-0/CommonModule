package fit.asta.health.feature.water.viewmodel

sealed class WaterState {
    data object Loading : WaterState()
    data object Success : WaterState()
    class Error(val error: Throwable) : WaterState()
}