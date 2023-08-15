package fit.asta.health.common.address.ui.view

import fit.asta.health.common.address.data.modal.MyAddress

internal sealed interface SavedAddressUiEvent {
    data class Search(val query: String) : SavedAddressUiEvent
    object ClearSearch : SavedAddressUiEvent
    data class SetSelectedAddress(val id: String) : SavedAddressUiEvent
    data class SelectAddress(val address: MyAddress) : SavedAddressUiEvent
    data class DeleteAddress(val id: String) : SavedAddressUiEvent
    object GetSavedAddress : SavedAddressUiEvent
    data class NavigateToMaps(val address: MyAddress) : SavedAddressUiEvent
    object Back : SavedAddressUiEvent
    object ResetDelete: SavedAddressUiEvent
    object ResetSelect: SavedAddressUiEvent
    object UpdateCurrentLocation : SavedAddressUiEvent
}