package fit.asta.health.network.interceptor

import fit.asta.health.network.NetworkHelper
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.TimeUnit

class OnlineInterceptor(private val networkHelper: NetworkHelper) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var response = chain.proceed(chain.request())
        val cacheControl: CacheControl = if (networkHelper.isConnected()) {
            CacheControl.Builder()
                .maxAge(0, TimeUnit.SECONDS)
                .build()
        } else {
            CacheControl.Builder()
                .maxStale(7, TimeUnit.DAYS)
                .build()
        }

        response = response.newBuilder()
            .removeHeader("Pragma")
            .header("Cache-Control", cacheControl.toString())
            .build()
        return response
    }
}