package fit.asta.health.home.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fit.asta.health.home.repo.ToolsHomeRepo
import fit.asta.health.home.repo.ToolsHomeRepoImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class HomeBindsModule {
    @Binds
    abstract fun provideHomeToolsRepo(toolsHomeRepoImpl: ToolsHomeRepoImpl): ToolsHomeRepo
}