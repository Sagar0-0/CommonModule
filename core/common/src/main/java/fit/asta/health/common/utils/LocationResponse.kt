package fit.asta.health.common.utils

import com.google.android.gms.maps.model.LatLng

sealed interface LocationResponse {
    data class Success(val latLng: LatLng) : LocationResponse
    data class Error(val resId: Int) : LocationResponse
    object PermissionDenied : LocationResponse
    object ServiceDisabled : LocationResponse
}