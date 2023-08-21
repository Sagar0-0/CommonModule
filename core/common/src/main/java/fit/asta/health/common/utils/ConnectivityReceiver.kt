package fit.asta.health.common.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

class ConnectivityReceiver(private val listener: ConnectivityListener?) : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        listener?.onNetworkStateChanged(isConnected(context))
    }

    private fun isConnected(context: Context): Boolean {

        val conMgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            val nw = conMgr.activeNetwork ?: return false
            val actNw = conMgr.getNetworkCapabilities(nw) ?: return false
            return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {

            val nwInfo = conMgr.activeNetworkInfo ?: return false
            return nwInfo.isConnected
        }
    }

    interface ConnectivityListener {

        fun onNetworkStateChanged(isConnected: Boolean)
    }
}