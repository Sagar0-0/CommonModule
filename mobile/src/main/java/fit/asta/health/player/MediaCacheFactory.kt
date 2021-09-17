package fit.asta.health.player

import android.content.Context
import android.net.Uri
import com.google.android.exoplayer2.database.DatabaseProvider
import com.google.android.exoplayer2.database.ExoDatabaseProvider
import com.google.android.exoplayer2.upstream.*
import com.google.android.exoplayer2.upstream.cache.CacheDataSink
import com.google.android.exoplayer2.upstream.cache.CacheDataSource
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor
import com.google.android.exoplayer2.upstream.cache.SimpleCache
import com.google.android.exoplayer2.util.Util
import java.io.File


open class SingletonHolder<out T : Any, in A, in B, in C>(creator: (A, B, C) -> T) {
    private var creator: ((A, B, C) -> T)? = creator
    @Volatile
    private var instance: T? = null

    fun getInstance(context: A, cacheSize: B, fileSize: C): T {

        val checkInstance = instance
        if (checkInstance != null) {
            return checkInstance
        }

        return synchronized(this) {
            val checkInstanceAgain = instance
            if (checkInstanceAgain != null) {
                checkInstanceAgain
            } else {
                val created = creator!!(context, cacheSize, fileSize)
                instance = created
                creator = null
                created
            }
        }
    }
}

internal class VideoCacheFactory private constructor(
    val context: Context,
    cacheSize: Long,
    private val fileSize: Long
) : DataSource.Factory {

    private val dataSourceFactory: ResolvingDataSource.Factory
    private val simpleCache: SimpleCache

    override fun createDataSource(): DataSource {

        return CacheDataSource(
            simpleCache,
            dataSourceFactory.createDataSource(),
            FileDataSource(),
            CacheDataSink(simpleCache, fileSize),
            CacheDataSource.FLAG_BLOCK_ON_CACHE or CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR,
            null
        )
    }

    init {

        val cacheEvictor = LeastRecentlyUsedCacheEvictor(cacheSize)
        val databaseProvider: DatabaseProvider = ExoDatabaseProvider(context)
        val cacheFolder = File(context.cacheDir, "media")

        simpleCache = SimpleCache(cacheFolder, cacheEvictor, databaseProvider)

        val userAgent: String = Util.getUserAgent(context, "userAgentVideo")
        dataSourceFactory = ResolvingDataSource.Factory(
            DefaultHttpDataSourceFactory(userAgent),
            ResolvingDataSource.Resolver { dataSpec: DataSpec ->
                dataSpec.withUri(
                    addToken(
                        dataSpec.uri
                    )
                )
            }
        )
    }

    private fun addToken(uri: Uri): Uri {

        return uri
    }

    companion object : SingletonHolder<VideoCacheFactory, Context, Long, Long>(::VideoCacheFactory)
}