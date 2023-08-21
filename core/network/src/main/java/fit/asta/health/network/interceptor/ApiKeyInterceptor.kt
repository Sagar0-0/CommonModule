package fit.asta.health.network.interceptor

import fit.asta.health.network.TokenProvider
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor(private val apiKey: TokenProvider) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val newRequest = request.newBuilder()
            .addHeader("auth-token", apiKey.get())
            .build()

        return chain.proceed(newRequest)
    }
}