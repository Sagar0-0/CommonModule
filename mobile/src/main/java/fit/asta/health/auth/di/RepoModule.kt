package fit.asta.health.auth.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import fit.asta.health.auth.model.AuthRepo
import fit.asta.health.auth.model.AuthRepoImpl

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideAuthRepo(
        repo: AuthRepoImpl
    ): AuthRepo
}