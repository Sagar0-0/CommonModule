package fit.asta.health.feature.address.view

import fit.asta.health.data.address.remote.modal.MyAddress

internal sealed interface SavedAddressUiEvent {
    data class Search(val query: String) : SavedAddressUiEvent
    data object ClearSearch : SavedAddressUiEvent
    data class SetSelectedAddress(val id: String) : SavedAddressUiEvent
    data class SelectAddress(val address: MyAddress) : SavedAddressUiEvent
    data class DeleteAddress(val id: String) : SavedAddressUiEvent
    data object GetSavedAddress : SavedAddressUiEvent
    data class NavigateToMaps(val address: MyAddress, val type: Int) :
        SavedAddressUiEvent//Type: 1->Search,2->New, 3->Edit

    class PutAddress(val myAddress: MyAddress) : SavedAddressUiEvent
    data object Back : SavedAddressUiEvent
    data object ResetDelete : SavedAddressUiEvent
    data object ResetSelect : SavedAddressUiEvent
    data object UpdateCurrentLocation : SavedAddressUiEvent
    data object ResetPutState : SavedAddressUiEvent
}