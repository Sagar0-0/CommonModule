package fit.asta.health.feature.address.view

import com.google.android.gms.maps.model.LatLng
import fit.asta.health.data.address.remote.modal.MyAddress

internal sealed interface MapScreenUiEvent {
    data class GetMarkerAddress(val latLng: LatLng) : MapScreenUiEvent
    data class Search(val query: String) : MapScreenUiEvent
    data class PutAddress(val address: MyAddress) : MapScreenUiEvent
    data object Back : MapScreenUiEvent
    data object ResetPutState : MapScreenUiEvent
    data object ClearSearch : MapScreenUiEvent
    data object UseCurrentLocation : MapScreenUiEvent
}