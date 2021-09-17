package fit.asta.health.network.interceptor

import android.content.Context
import fit.asta.health.network.Connectivity
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.TimeUnit

class OfflineInterceptor(private val context : Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        if (!Connectivity.isConnected(context)) {
            val cacheControl = CacheControl.Builder()
                .maxStale(30, TimeUnit.DAYS)
                .build()

            request = request.newBuilder()
                .removeHeader("Pragma")
                .cacheControl(cacheControl)
                .build()
        }

        return chain.proceed(request)
    }
}