package fit.asta.health.profile.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import fit.asta.health.profile.model.ProfileDataMapper
import fit.asta.health.profile.model.ProfileRepo
import fit.asta.health.profile.model.ProfileRepoImpl
import fit.asta.health.profile.model.api.ProfileApi
import fit.asta.health.profile.model.api.ProfileRestApi
import okhttp3.OkHttpClient
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ProfileModule {

    @Singleton
    @Provides
    fun provideProfileApi(client: OkHttpClient): ProfileApi {
        return ProfileRestApi(client)
    }

    @Singleton
    @Provides
    fun provideProfileMapper(): ProfileDataMapper {
        return ProfileDataMapper()
    }

    @Singleton
    @Provides
    fun provideProfileRepo(
        @ApplicationContext context: Context,
        remoteApi: ProfileApi,
        recipeMapper: ProfileDataMapper,
    ): ProfileRepo {
        return ProfileRepoImpl(remoteApi = remoteApi, mapper = recipeMapper, context = context)
    }
}