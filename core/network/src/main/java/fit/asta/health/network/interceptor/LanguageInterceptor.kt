package fit.asta.health.network.interceptor

import fit.asta.health.network.LanguageProvider
import okhttp3.Interceptor
import okhttp3.Response

class LanguageInterceptor(private val language: LanguageProvider) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val newRequest = request.newBuilder()
            .addHeader("lang", language.get())
            .build()

        return chain.proceed(newRequest)
    }
}