package fit.asta.health.datastore

sealed class ScreenCode(val code: Int) {
    data object Auth : ScreenCode(0)
    data object BasicProfile : ScreenCode(1)
    data object Home : ScreenCode(2)
}
