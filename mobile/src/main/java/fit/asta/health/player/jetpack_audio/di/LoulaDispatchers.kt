package fit.asta.health.player.jetpack_audio.di

import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention.RUNTIME

@Qualifier
@Retention(RUNTIME)
annotation class Dispatcher(val loulaDispatcher: LoulaDispatchers)

enum class LoulaDispatchers { DEFAULT, MAIN, UNCONFINED, IO }
