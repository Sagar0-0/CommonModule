package fit.asta.health.fcm.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fit.asta.health.fcm.repo.TokenRepo
import fit.asta.health.fcm.repo.TokenRepoImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TokenModule {

    @Provides
    @Singleton
    fun provideTokenRepo(): TokenRepo = TokenRepoImpl()
}