package fit.asta.health.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import fit.asta.health.notify.reminder.data.Reminder
import fit.asta.health.notify.reminder.db.ReminderDao

const val DATABASE_SCHEMA_VERSION = 1

@Database(version = DATABASE_SCHEMA_VERSION, entities = [Reminder::class])
abstract class AppDb : RoomDatabase() {

    // Insert DAOs below
    abstract fun reminderDAO(): ReminderDao

    companion object {

        private const val DB_NAME = "health-db"

        private val MIGRATION_1_TO_2 = object : Migration(1, 2) {

            override fun migrate(database: SupportSQLiteDatabase) {

                //database.execSQL("ALTER TABLE reminders ADD COLUMN createdAt LONG")
            }
        }

        private val MIGRATION_2_TO_3 = object : Migration(2, 3) {

            override fun migrate(database: SupportSQLiteDatabase) {

                //database.execSQL("CREATE INDEX 'indexLinkedId' ON list_items('linkedId')")
            }
        }

        fun createDatabase(context: Context): AppDb {

            return Room.databaseBuilder(
                context, AppDb::class.java,
                DB_NAME
            )
                //.fallbackToDestructiveMigration()
                .addMigrations(
                    MIGRATION_1_TO_2,
                    MIGRATION_2_TO_3
                )
                .build()
        }
    }
}
