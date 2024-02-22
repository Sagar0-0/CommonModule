package fit.asta.health.data.profile.di

import android.content.Context
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import fit.asta.health.data.profile.local.ProfileDao
import fit.asta.health.data.profile.local.ProfileDatabase
import fit.asta.health.data.profile.remote.ProfileApi
import fit.asta.health.data.profile.repo.ProfileRepo
import fit.asta.health.data.profile.repo.ProfileRepoImpl
import fit.asta.health.network.utils.NetworkUtil
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProfileModule {

    @Singleton
    @Provides
    fun provideProfileApi(client: OkHttpClient): ProfileApi =
        NetworkUtil.getRetrofit(client).create(ProfileApi::class.java)

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context,
    ) = Room.databaseBuilder(
        context,
        ProfileDatabase::class.java,
        "profile-database"
    ).build()

    @Singleton
    @Provides
    fun provideDao(db: ProfileDatabase): ProfileDao = db.getDao()

}

@Module
@InstallIn(SingletonComponent::class)
abstract class ProfileBindsModule {
    @Binds
    abstract fun provideProfileRepo(profileRepoImpl: ProfileRepoImpl): ProfileRepo
}

