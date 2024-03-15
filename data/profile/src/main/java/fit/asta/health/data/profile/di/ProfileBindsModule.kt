package fit.asta.health.data.profile.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fit.asta.health.data.profile.repo.ProfileRepo
import fit.asta.health.data.profile.repo.ProfileRepoImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class ProfileBindsModule {
    @Binds
    abstract fun provideProfileRepo(profileRepoImpl: ProfileRepoImpl): ProfileRepo
}