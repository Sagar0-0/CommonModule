package fit.asta.health.feature.address.view

import fit.asta.health.data.address.remote.modal.MyAddress

sealed interface FillAddressSheetType {
    data class SaveCurrentAddress(val address: MyAddress) : FillAddressSheetType
    data class EnterNewAddress(val address: MyAddress) : FillAddressSheetType
}
