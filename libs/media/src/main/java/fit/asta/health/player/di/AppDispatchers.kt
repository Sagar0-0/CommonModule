package fit.asta.health.player.di

import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention.RUNTIME

@Qualifier
@Retention(RUNTIME)
annotation class Dispatcher(val dispatcher: AppDispatchers)

enum class AppDispatchers { DEFAULT, MAIN, UNCONFINED, IO }
