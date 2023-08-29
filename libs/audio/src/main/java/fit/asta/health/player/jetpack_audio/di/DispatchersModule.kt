package fit.asta.health.player.jetpack_audio.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fit.asta.health.player.jetpack_audio.di.AppDispatchers.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object DispatchersModule {
    @Provides
    @Dispatcher(DEFAULT)
    fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @Provides
    @Dispatcher(MAIN)
    fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

    @Provides
    @Dispatcher(UNCONFINED)
    fun provideUnconfinedDispatcher(): CoroutineDispatcher = Dispatchers.Unconfined

    @Provides
    @Dispatcher(IO)
    fun provideIODispatcher(): CoroutineDispatcher = Dispatchers.IO
}
