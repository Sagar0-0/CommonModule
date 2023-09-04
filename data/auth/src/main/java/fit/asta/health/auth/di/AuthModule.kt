package fit.asta.health.auth.di

import com.google.firebase.auth.FirebaseAuth
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fit.asta.health.auth.fcm.remote.TokenApi
import fit.asta.health.auth.model.AuthDataMapper
import fit.asta.health.auth.remote.AuthApi
import fit.asta.health.auth.repo.AuthRepo
import fit.asta.health.auth.repo.AuthRepoImpl
import fit.asta.health.datastore.PrefManager
import fit.asta.health.network.utils.NetworkUtil
import okhttp3.OkHttpClient
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Singleton
    @Provides
    fun provideAuthApi(client: OkHttpClient): AuthApi =
        NetworkUtil.getRetrofit(client).create(AuthApi::class.java)


    @Singleton
    @Provides
    fun provideAuthDataMapper(): AuthDataMapper {
        return AuthDataMapper()
    }


    @Provides
    @UID
    fun provideUId(authRepo: AuthRepo): String = authRepo.getUserId() ?: ""

}

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthBindModule{
    @Binds
    abstract fun provideAuthRepo(authRepoImpl: AuthRepoImpl) : AuthRepo

}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class UID