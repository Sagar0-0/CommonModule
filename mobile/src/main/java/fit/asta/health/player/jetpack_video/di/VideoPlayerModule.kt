package fit.asta.health.player.jetpack_video.di

import android.content.Context
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.Player
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object VideoPlayerModule {

    @Provides
    @androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
    @ViewModelScoped
    fun  provideVideoPlayer(@ApplicationContext context: Context): Player {
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
            .setWakeMode(C.WAKE_MODE_LOCAL)
            .build().apply {
                playWhenReady = true
            }
    }


}