package fit.asta.health.network

import fit.asta.health.BuildConfig
import fit.asta.health.network.api.RemoteApis
import fit.asta.health.network.api.RestApi
import fit.asta.health.network.interceptor.ApiKeyInterceptor
import fit.asta.health.utils.NetworkUtil
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.experimental.property.inject

class AstaNetwork private constructor() {

    companion object {
        const val CACHE_SIZE: Long = 10 * 1024 * 1024
    }

    class Builder {
        private var cache: Cache? = null
        private var apiKey: TokenProvider?  = null
        private var baseUrl: String = BuildConfig.BASE_URL
        private val interceptors = arrayListOf<Interceptor>()
        private val networkInterceptors = arrayListOf<Interceptor>()
        private val certificates = arrayListOf<Certificate>()


        fun setCache(cache: Cache) = apply {
            this.cache = cache
        }

        fun setApiKey(key: TokenProvider) = apply {
            apiKey = key
        }

        fun setBaseUrl(url: String) = apply {
            baseUrl = url
        }

        fun addInterceptor(interceptor: Interceptor) = apply {
            interceptors.add(interceptor)
        }

        fun addNetworkInterceptor(interceptor: Interceptor) = apply {
            networkInterceptors.add(interceptor)
        }

        fun addCertificatePinner(certificate: Certificate) = apply {
            certificates.add(certificate)
        }

        private fun createClient(): OkHttpClient {
            val certificatePinner = NetworkUtil.buildCertificatePinner(certificates)
            networkInterceptors.add(0,
                ApiKeyInterceptor(apiKey!!)
            )
            return NetworkUtil.getOkHttpClient(
                interceptors,
                networkInterceptors,
                certificatePinner,
                cache
            )
        }

        fun build(): RemoteApis {
            val client = createClient()
            return RestApi(baseUrl, client)
        }
    }
}