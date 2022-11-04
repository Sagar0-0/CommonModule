package fit.asta.health.firebase.model.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fit.asta.health.firebase.model.service.AuthService
import fit.asta.health.firebase.model.service.ConfigService
import fit.asta.health.firebase.model.service.CrashLogService
import fit.asta.health.firebase.model.service.StorageService
import fit.asta.health.firebase.model.service.impl.AccountServiceImpl
import fit.asta.health.firebase.model.service.impl.ConfigServiceImpl
import fit.asta.health.firebase.model.service.impl.CrashLogServiceImpl
import fit.asta.health.firebase.model.service.impl.StorageServiceImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {
  @Binds
  abstract fun provideAccountService(impl: AccountServiceImpl): AuthService

  @Binds
  abstract fun provideLogService(impl: CrashLogServiceImpl): CrashLogService

  @Binds
  abstract fun provideStorageService(impl: StorageServiceImpl): StorageService

  @Binds
  abstract fun provideConfigurationService(impl: ConfigServiceImpl): ConfigService
}
