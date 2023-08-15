package fit.asta.health.common.address.ui.view

import fit.asta.health.common.address.data.modal.MyAddress

internal sealed interface FillAddressUiEvent {
    object Back : FillAddressUiEvent
    object CloseSheet : FillAddressUiEvent
    data class SaveAddress(val myAddress: MyAddress) : FillAddressUiEvent
}