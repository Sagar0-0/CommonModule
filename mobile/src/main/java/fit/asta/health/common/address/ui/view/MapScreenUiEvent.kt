package fit.asta.health.common.address.ui.view

import com.google.android.gms.maps.model.LatLng
import fit.asta.health.common.address.data.modal.MyAddress

internal sealed interface MapScreenUiEvent {
    data class GetMarkerAddress(val latLng: LatLng) : MapScreenUiEvent
    object Back : MapScreenUiEvent
    data class Search(val query: String) : MapScreenUiEvent
    object ResetPutState: MapScreenUiEvent
    object ClearSearch : MapScreenUiEvent
    data class PutAddress(val myAddress: MyAddress) : MapScreenUiEvent
    object UseCurrentLocation : MapScreenUiEvent
}