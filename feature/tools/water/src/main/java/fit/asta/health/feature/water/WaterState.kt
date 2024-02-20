package fit.asta.health.feature.water

sealed class WaterState {
    data object Loading : WaterState()
    data object Success : WaterState()
    class Error(val error: String) : WaterState()
}