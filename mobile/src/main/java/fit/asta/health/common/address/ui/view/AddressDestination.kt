package fit.asta.health.common.address.ui.view

internal sealed class AddressDestination(val route: String) {
    object Map : AddressDestination("Map")
    object SavedAdd : AddressDestination("Saved")
}
