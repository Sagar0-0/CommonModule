package fit.asta.health.di

import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import fit.asta.health.HealthCareApp
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @IODispatcher
    fun provideIODispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @DefaultDispatcher
    fun providesCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): HealthCareApp {
        return app as HealthCareApp
    }

    @Provides
    @Singleton
    fun providesSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("shared", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            produceFile = { context.preferencesDataStoreFile("USER_PREFERENCES") }
        )
    }

    @Provides
    @Singleton
    @ApplicationScope
    fun providesCoroutineScope(
        @IODispatcher dispatcher: CoroutineDispatcher,
    ): CoroutineScope = CoroutineScope(SupervisorJob() + dispatcher)

    @Provides
    @androidx.annotation.OptIn(UnstableApi::class)
    @Singleton
    fun provideVideoPlayer(@ApplicationContext context: Context): Player {
        val audioAttributes = AudioAttributes.Builder()
            .setContentType(C.AUDIO_CONTENT_TYPE_MOVIE)
            .setUsage(C.USAGE_MEDIA)
            .build()
        return ExoPlayer.Builder(context)
            .setMediaSourceFactory(
                ProgressiveMediaSource.Factory(DefaultDataSource.Factory(context))
            )
            .setAudioAttributes(audioAttributes, true)
            .setHandleAudioBecomingNoisy(true)
            .setTrackSelector(DefaultTrackSelector(context).apply {
                this.setParameters(
                    this.buildUponParameters()
                        .setPreferredAudioLanguage("hindi")
                )
            })
            .setWakeMode(C.WAKE_MODE_LOCAL)
            .build().apply {
                playWhenReady = true
            }
    }
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IODispatcher


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DefaultDispatcher

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
private annotation class ApplicationScope