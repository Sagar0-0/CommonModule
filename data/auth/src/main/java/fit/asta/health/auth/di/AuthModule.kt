package fit.asta.health.auth.di

import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fit.asta.health.auth.model.AuthDataMapper
import fit.asta.health.auth.repo.AuthRepo
import fit.asta.health.auth.repo.AuthRepoImpl
import fit.asta.health.datastore.PrefManager
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Singleton
    @Provides
    fun provideAuthDataMapper(): AuthDataMapper {
        return AuthDataMapper()
    }

    @Provides
    @UID
    fun provideUId(authRepo: AuthRepo): String = authRepo.getUserId() ?: ""

    @Singleton
    @Provides
    fun provideAuthRepo(
        dataMapper: AuthDataMapper,
        firebaseAuth: FirebaseAuth,
        prefManager: PrefManager
    ): AuthRepo {
        return AuthRepoImpl(dataMapper, firebaseAuth, prefManager)
    }
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class UID