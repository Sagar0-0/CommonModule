package fit.asta.health.data.address.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.location.LocationManager

class LocationProviderChangedReceiver(
    val onToggleLocation: () -> Unit
) : BroadcastReceiver() {

    private var isGpsEnabled: Boolean = false
    private var isNetworkEnabled: Boolean = false
    private var registered = false

    override fun onReceive(context: Context, intent: Intent) {
        intent.action?.let { act ->
            if (act == LocationManager.PROVIDERS_CHANGED_ACTION) {
                onToggleLocation()
//                val locationManager =
//                    context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
//                isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
//                isNetworkEnabled =
//                    locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
//
//                Log.i(TAG, "Location Providers changed, is GPS Enabled: $isGpsEnabled")
//
//                //Start your service if location was enabled:
//                if (isGpsEnabled && isNetworkEnabled) {
//                    onEnabled()
//                } else {
//                    onDisabled()
//                }
            }
        }
    }

    fun register(context: Context) {
        if (!registered) {
            val filter = IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION)
            context.registerReceiver(this, filter)
            registered = true
        }
    }

    fun unregister(context: Context) {
        if (registered) {
            context.unregisterReceiver(this)
            registered = false
        }
    }

    companion object {
        private const val TAG = "Location"
    }
}