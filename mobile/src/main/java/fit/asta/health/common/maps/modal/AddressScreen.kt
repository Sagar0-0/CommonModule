package fit.asta.health.common.maps.modal

sealed class AddressScreen(val route: String) {
    object Map : AddressScreen("Map")
    object SavedAdd : AddressScreen("Saved")
}
