package fit.asta.health.thirdparty.spotify.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import fit.asta.health.network.utils.NetworkUtil
import fit.asta.health.thirdparty.spotify.model.MusicRepository
import fit.asta.health.thirdparty.spotify.model.MusicRepositoryImpl
import fit.asta.health.thirdparty.spotify.model.SpotifyRepo
import fit.asta.health.thirdparty.spotify.model.SpotifyRepoImpl
import fit.asta.health.thirdparty.spotify.model.api.SpotifyApiService
import fit.asta.health.thirdparty.spotify.model.db.MusicDataSourceImpl
import fit.asta.health.thirdparty.spotify.model.db.MusicDao
import fit.asta.health.thirdparty.spotify.model.db.MusicDataSource
import fit.asta.health.thirdparty.spotify.model.db.MusicDatabase
import fit.asta.health.thirdparty.spotify.utils.SpotifyConstants.Companion.SPOTIFY_BASE_URL
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SpotifyNetworkModule {

    @Singleton
    @Provides
    fun provideSpotifyApiService(client: OkHttpClient): SpotifyApiService {
        return NetworkUtil
            .getRetrofit(SPOTIFY_BASE_URL, client)
            .create(SpotifyApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideSpotifyRepo(api: SpotifyApiService): SpotifyRepo {
        return SpotifyRepoImpl(spotifyApi = api)
    }

    @Singleton
    @Provides
    fun provideMusicDatabase(@ApplicationContext context: Context): MusicDatabase {
        return Room.databaseBuilder(
            context,
            MusicDatabase::class.java,
            name = "music-database"
        ).build()
    }

    @Singleton
    @Provides
    fun provideMusicDao(db: MusicDatabase): MusicDao {
        return db.musicDao()
    }

    @Singleton
    @Provides
    fun provideMusicDataSource(dao: MusicDao): MusicDataSource {
        return MusicDataSourceImpl(dao)
    }

    @Singleton
    @Provides
    fun provideMusicRepository(localDataSource: MusicDataSource): MusicRepository {
        return MusicRepositoryImpl(localDataSource)
    }
}