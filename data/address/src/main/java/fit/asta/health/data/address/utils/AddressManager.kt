package fit.asta.health.data.address.utils

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Build
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.qualifiers.ApplicationContext
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.resources.strings.R.string
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.util.Locale
import javax.inject.Inject

class AddressManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private fun getGeoCoder(): Geocoder = Geocoder(context, Locale.getDefault())

    suspend fun getAddressDetails(latLng: LatLng): Flow<ResponseState<Address>> {
        return callbackFlow {
            try {
                val geocoder = getGeoCoder()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    geocoder.getFromLocation(
                        latLng.latitude,
                        latLng.longitude,
                        1
                    ) { addresses ->
                        trySend(ResponseState.Success(addresses[0]))
                    }
                } else {
                    val addresses = geocoder.getFromLocation(
                        latLng.latitude,
                        latLng.longitude,
                        1,
                    )
                    if (!addresses.isNullOrEmpty()) {
                        trySend(ResponseState.Success(addresses[0]))
                    } else {
                        trySend(ResponseState.ErrorMessage(string.unable_to_fetch_location))
                    }
                }
            } catch (e: Exception) {
                trySend(ResponseState.ErrorMessage(string.unable_to_fetch_location))
            }

            awaitClose { close() }
        }
    }
}