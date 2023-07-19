package fit.asta.health.auth.di

import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fit.asta.health.auth.model.AuthDataMapper
import fit.asta.health.auth.model.AuthRepo
import fit.asta.health.auth.model.FirebaseAuthRepoImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideAuthDataMapper(): AuthDataMapper {
        return AuthDataMapper()
    }

    @Singleton
    @Provides
    fun provideAuthRepo(dataMapper: AuthDataMapper, firebaseAuth: FirebaseAuth): AuthRepo {
        return FirebaseAuthRepoImpl(dataMapper, firebaseAuth)
    }
}