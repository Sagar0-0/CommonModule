package fit.asta.health.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import fit.asta.health.data.scheduler.local.AlarmDatabase
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideAlarmDatabase(
        @ApplicationContext context: Context,
    ) = Room.databaseBuilder(
        context,
        AlarmDatabase::class.java,
        "alarm-database"
    ).build()

    @Singleton
    @Provides
    fun provideAlarmDao(database: AlarmDatabase) = database.alarmDao()


    @Singleton
    @Provides
    fun provideAlarmInstanceDao(database: AlarmDatabase) = database.alarmInstanceDao()

//    @Singleton
//    @Provides
//    fun provideMusicDatabase(
//        @ApplicationContext context: Context,
//    ) = Room.databaseBuilder(
//        context,
//        MusicDatabase::class.java,
//        "music-database"
//    ).build()
//
//    @Singleton
//    @Provides
//    fun provideMusicDao(database: MusicDatabase) = database.musicDao()
}