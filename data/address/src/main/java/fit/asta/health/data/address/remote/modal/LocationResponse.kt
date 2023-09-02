package fit.asta.health.data.address.remote.modal

import com.google.android.gms.maps.model.LatLng

sealed interface LocationResponse {
    data class Success(val latLng: LatLng) : LocationResponse
    data class Error(val resId: Int) : LocationResponse
    data object PermissionDenied : LocationResponse
    data object ServiceDisabled : LocationResponse
}