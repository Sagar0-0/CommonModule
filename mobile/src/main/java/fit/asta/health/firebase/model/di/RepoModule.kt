package fit.asta.health.firebase.model.di

import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fit.asta.health.firebase.model.AuthDataMapper
import fit.asta.health.firebase.model.AuthRepo
import fit.asta.health.firebase.model.FirebaseAuthRepoImpl
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