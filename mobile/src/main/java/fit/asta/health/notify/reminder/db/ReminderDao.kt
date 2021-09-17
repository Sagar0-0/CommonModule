package fit.asta.health.notify.reminder.db

import androidx.lifecycle.LiveData
import androidx.room.*
import fit.asta.health.notify.reminder.data.Reminder

@Dao
interface ReminderDao {

    @Insert
    fun add(reminder: Reminder)

    @Update
    fun update(reminder: Reminder)

    @Delete
    fun delete(reminder: Reminder)

    @Query("SELECT * FROM reminders")
    fun getAll(): LiveData<List<Reminder>>

    @Query("SELECT * FROM reminders where id=:id")
    fun getById(id: String): LiveData<Reminder>

    @Query("SELECT * FROM reminders where linkedId=:linkedId")
    fun getByLinkId(linkedId: String): LiveData<List<Reminder>>
}