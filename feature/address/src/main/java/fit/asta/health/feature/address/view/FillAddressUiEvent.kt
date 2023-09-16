package fit.asta.health.feature.address.view

import fit.asta.health.data.address.remote.modal.MyAddress

internal sealed interface FillAddressUiEvent {
    data object OnPutSuccess : FillAddressUiEvent
    data object CloseSheet : FillAddressUiEvent
    data class SaveAddress(val myAddress: MyAddress) : FillAddressUiEvent

    data object ResetPutState : FillAddressUiEvent
}