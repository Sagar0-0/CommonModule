package fit.asta.health.common.location.maps.modal

sealed class MapScreens(val route: String) {
    object Map : MapScreens("Map")
    object SavedAdd : MapScreens("Saved")
}
