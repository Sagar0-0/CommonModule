package fit.asta.health.notify.reminder

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import fit.asta.health.notify.reminder.data.Reminder
import fit.asta.health.notify.reminder.db.ReminderRepo
import fit.asta.health.notify.reminder.db.StatusCallback

class ReminderViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = ReminderRepo()

    suspend fun add(reminder: Reminder, callback: StatusCallback) {

        repo.add(reminder, callback)
    }

    suspend fun update(reminder: Reminder, callback: StatusCallback) {

        repo.update(reminder, callback)
    }

    suspend fun delete(reminder: Reminder, callback: StatusCallback) {

        repo.delete(reminder, callback)
    }

    fun getById(id: String): LiveData<Reminder> {

        return repo.getById(id)
    }

    fun getAll(): LiveData<List<Reminder>> {

        return repo.getAll()
    }

    fun getByLinkId(id: String): LiveData<List<Reminder>> {

        return repo.getByLinkId(id)
    }
}