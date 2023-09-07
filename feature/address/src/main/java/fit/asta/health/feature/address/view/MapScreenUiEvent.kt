package fit.asta.health.feature.address.view

import com.google.android.gms.maps.model.LatLng
import fit.asta.health.data.address.remote.modal.MyAddress

internal sealed interface MapScreenUiEvent {
    data class GetMarkerAddress(val latLng: LatLng) : MapScreenUiEvent
    data object Back : MapScreenUiEvent
    data object UseCurrentLocation : MapScreenUiEvent
    data object ShowSearchSheet : MapScreenUiEvent
    class ShowFillAddressSheet(val myAddress: MyAddress) : MapScreenUiEvent
}