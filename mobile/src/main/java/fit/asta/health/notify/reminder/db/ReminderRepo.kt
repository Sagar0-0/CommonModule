package fit.asta.health.notify.reminder.db

import android.util.Log
import androidx.lifecycle.LiveData
import fit.asta.health.HealthCareApp
import fit.asta.health.notify.reminder.data.Reminder
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.withTimeout


const val TIMEOUT_DURATION_MILLIS = 3000L
typealias StatusCallback = (Boolean) -> Unit

class ReminderRepo {

    private val reminderDao = HealthCareApp.appDb.reminderDAO()

    private suspend fun performOperation(func: () -> Unit, callback: StatusCallback) {

        val job = GlobalScope.async {

            try {

                withTimeout(TIMEOUT_DURATION_MILLIS) {

                    func.invoke()
                }
            } catch (e: java.lang.Exception) {

                Log.e("dbOperation", e.message!!)
                callback.invoke(false)
            }

            callback.invoke(true)
        }

        job.await()
    }

    suspend fun add(reminder: Reminder, callback: StatusCallback) {

        performOperation({ reminderDao.add(reminder) }, callback)
    }

    suspend fun update(reminder: Reminder, callback: StatusCallback) {

        performOperation({ reminderDao.update(reminder) }, callback)
    }

    suspend fun delete(reminder: Reminder, callback: StatusCallback) {

        performOperation({ reminderDao.delete(reminder) }, callback)
    }

    fun getById(id: String): LiveData<Reminder> {

        return reminderDao.getById(id)
    }

    fun getAll(): LiveData<List<Reminder>> {

        return reminderDao.getAll()
    }

    fun getByLinkId(id: String): LiveData<List<Reminder>> {

        return reminderDao.getByLinkId(id)
    }
}