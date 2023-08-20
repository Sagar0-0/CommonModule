package com.example.auth.di

import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fit.asta.health.auth.data.model.AuthDataMapper
import fit.asta.health.auth.data.repo.AuthRepo
import fit.asta.health.auth.data.repo.AuthRepoImpl
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

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
    fun provideAuthRepo(dataMapper: AuthDataMapper, firebaseAuth: FirebaseAuth): AuthRepo {
        return AuthRepoImpl(dataMapper, firebaseAuth)
    }
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class UID