package com.example.auth.di

import com.example.auth.model.AuthDataMapper
import com.example.auth.repo.AuthRepo
import com.example.auth.repo.AuthRepoImpl
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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