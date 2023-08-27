package fit.asta.health.data.spotify.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import fit.asta.health.data.spotify.SpotifyConstants.Companion.SPOTIFY_BASE_URL
import fit.asta.health.network.utils.NetworkUtil
import fit.asta.health.data.spotify.repo.MusicRepository
import fit.asta.health.data.spotify.repo.MusicRepositoryImpl
import fit.asta.health.data.spotify.repo.SpotifyRepo
import fit.asta.health.data.spotify.repo.SpotifyRepoImpl
import fit.asta.health.data.spotify.remote.SpotifyApiService
import fit.asta.health.data.spotify.local.MusicDatabase
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
    fun provideMusicRepository(db: MusicDatabase): MusicRepository {
        return MusicRepositoryImpl(db.musicDao())
    }
}