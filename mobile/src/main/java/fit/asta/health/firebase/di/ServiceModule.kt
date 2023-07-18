package fit.asta.health.firebase.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fit.asta.health.firebase.model.service.ConfigService
import fit.asta.health.firebase.model.service.CrashLogService
import fit.asta.health.firebase.model.service.impl.ConfigServiceImpl
import fit.asta.health.firebase.model.service.impl.CrashLogServiceImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {

  @Binds
  abstract fun provideLogService(impl: CrashLogServiceImpl): CrashLogService

  @Binds
  abstract fun provideConfigurationService(impl: ConfigServiceImpl): ConfigService
}
