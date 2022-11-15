package fit.asta.health.utils


import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import androidx.lifecycle.LiveData


class NetworkConnectivity(val context: Context) : LiveData<Boolean>() {

    private var cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private lateinit var cmCallback: ConnectivityManager.NetworkCallback

    override fun onActive() {
        super.onActive()

        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.N ->
                cm.registerDefaultNetworkCallback(getConnectivityManagerCallback())
            else -> lollipopNetworkAvailableRequest()
        }

        postValue(isConnected())
    }

    override fun onInactive() {
        super.onInactive()

        cm.unregisterNetworkCallback(cmCallback)
    }

    private fun lollipopNetworkAvailableRequest() {

        val builder = NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)

        cm.registerNetworkCallback(builder.build(), getConnectivityManagerCallback())
    }

    private fun getConnectivityManagerCallback(): ConnectivityManager.NetworkCallback {

        cmCallback = object : ConnectivityManager.NetworkCallback() {

            override fun onAvailable(network: Network) {

                postValue(true)
            }

            override fun onLost(network: Network) {

                postValue(false)
            }

            override fun onUnavailable() {

                postValue(false)
            }
        }

        return cmCallback
    }

    private fun isConnected(): Boolean {

        var result = false

        cm.run {

            cm.getNetworkCapabilities(cm.activeNetwork)?.run {

                result = when {

                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    else -> false
                }
            }
        }

        return result
    }
}