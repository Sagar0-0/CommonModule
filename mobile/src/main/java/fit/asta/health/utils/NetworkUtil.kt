package fit.asta.health.utils

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import fit.asta.health.network.Certificate
import okhttp3.Cache
import okhttp3.CertificatePinner
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.security.KeyStore
import java.security.SecureRandom
import java.text.DateFormat
import javax.net.ssl.KeyManagerFactory
import javax.net.ssl.SSLContext


object NetworkUtil {

    fun getRetrofit(baseUrl: String, client: OkHttpClient = getOkHttpClient()): Retrofit {

        val gson: Gson = GsonBuilder()
            .enableComplexMapKeySerialization()
            .serializeNulls()
            .setDateFormat(DateFormat.LONG)
            .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
            .setPrettyPrinting()
            .setVersion(1.0)
            .create()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    fun getOkHttpClient(
        interceptors: List<Interceptor> = listOf(),
        networkInterceptors: List<Interceptor> = listOf(),
        certificatePinner: CertificatePinner? = null,
        cache: Cache? = null
    ): OkHttpClient {
        val builder = OkHttpClient().newBuilder()

        cache?.let {
            builder.cache(it)
        }

        interceptors.map {
            builder.addInterceptor(it)
        }

        networkInterceptors.map {
            builder.addNetworkInterceptor(it)
        }

        certificatePinner?.let {
            builder.certificatePinner(buildCertificatePinner())
        }
        return builder.build()
    }

     fun buildCertificatePinner(pins: List<Certificate> = listOf()): CertificatePinner {
        val builder = CertificatePinner.Builder()

        pins.map {
            builder.add(
                it.hostName,
                *(it.pins.toTypedArray())
            )
        }
        return builder.build()
    }
}