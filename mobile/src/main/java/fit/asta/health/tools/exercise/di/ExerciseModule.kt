package fit.asta.health.tools.exercise.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import fit.asta.health.tools.exercise.db.ExerciseToolDatabase
import fit.asta.health.tools.exercise.model.ExerciseLocalRepo
import fit.asta.health.tools.exercise.model.ExerciseLocalRepoImp
import fit.asta.health.tools.exercise.model.ExerciseRepo
import fit.asta.health.tools.exercise.model.ExerciseRepoImp
import fit.asta.health.tools.exercise.model.api.ExerciseApi
import fit.asta.health.tools.exercise.model.api.ExerciseRestApi
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ExerciseModule {

    @Singleton
    @Provides
    fun provideApi(client: OkHttpClient): ExerciseApi {
        return ExerciseRestApi(client)
    }
    @Singleton
    @Provides
    fun provideRepo(api: ExerciseApi): ExerciseRepo {
        return ExerciseRepoImp(api)
    }
    @Singleton
    @Provides
    fun provideExerciseDatabase(
        @ApplicationContext context: Context,
    ) = Room.databaseBuilder(
        context,
        ExerciseToolDatabase::class.java,
        "exercise-database"
    ).build()


    @Singleton
    @Provides
    fun provideExerciseLocalRepo(db: ExerciseToolDatabase): ExerciseLocalRepo {
        return ExerciseLocalRepoImp(db.exerciseDao())
    }
}