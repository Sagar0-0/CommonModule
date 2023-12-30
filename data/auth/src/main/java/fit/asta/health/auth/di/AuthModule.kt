package fit.asta.health.auth.di

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import fit.asta.health.auth.model.AuthDataMapper
import fit.asta.health.auth.remote.AuthApi
import fit.asta.health.auth.repo.AuthRepo
import fit.asta.health.auth.repo.AuthRepoImpl
import fit.asta.health.network.utils.NetworkUtil
import fit.asta.health.resources.strings.R
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

    @Singleton
    @Provides
    fun provideGoogleSignInClient(
        @ApplicationContext context: Context,
        gso: GoogleSignInOptions
    ): GoogleSignInClient {
        return GoogleSignIn.getClient(context, gso)
    }

    @Singleton
    @Provides
    fun provideGoogleSignInOptions(@ApplicationContext context: Context): GoogleSignInOptions {
        return GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
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