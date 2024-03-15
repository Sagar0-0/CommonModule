package fit.asta.health.data.spotify.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import fit.asta.health.core.network.BuildConfig
import fit.asta.health.data.spotify.local.MusicDao
import fit.asta.health.data.spotify.local.MusicDatabase
import fit.asta.health.data.spotify.remote.SpotifyApi
import fit.asta.health.data.spotify.repo.MusicRepo
import fit.asta.health.data.spotify.repo.MusicRepoImpl
import fit.asta.health.data.spotify.repo.SpotifyRepo
import fit.asta.health.data.spotify.repo.SpotifyRepoImpl
import fit.asta.health.network.utils.NetworkUtil
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SpotifyModule {

    @Singleton
    @Provides
    fun provideSpotifyApiService(client: OkHttpClient): SpotifyApi {
        return NetworkUtil
            .getRetrofit(BuildConfig.SPOTIFY_BASE_URL, client)
            .create(SpotifyApi::class.java)
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
    fun provideMusicDao(db: MusicDatabase): MusicDao = db.musicDao()

    @Singleton
    @Provides
    fun provideSpotifyRepo(api: SpotifyApi): SpotifyRepo {
        return SpotifyRepoImpl(spotifyApi = api)
    }

    @Singleton
    @Provides
    fun provideMusicRepo(dao: MusicDao): MusicRepo {
        return MusicRepoImpl(musicDao = dao)
    }
}