package fit.asta.health.feature.address.view

internal sealed class AddressDestination(val route: String) {
    data object Map : AddressDestination("Map")
    data object SavedAdd : AddressDestination("Saved")
}
