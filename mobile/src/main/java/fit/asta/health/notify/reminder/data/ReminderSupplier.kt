package fit.asta.health.notify.reminder.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import fit.asta.health.utils.ONE_MEGABYTE
import fit.asta.health.utils.loadJSONFromAsset


class ReminderSupplier {

    private val mStorageRef: StorageReference? = FirebaseStorage.getInstance().reference

    fun getReminder(url: String): LiveData<List<Reminder>> {

        val mReminder = MutableLiveData<List<Reminder>>()
        val reminderRef: StorageReference = mStorageRef!!.child(url)
        reminderRef.getBytes(ONE_MEGABYTE).addOnSuccessListener { bytes ->

            parseReminder(bytes.toString(Charsets.UTF_8), mReminder)
        }

        return mReminder
    }

    private fun parseReminder(json: String, mCategory: MutableLiveData<List<Reminder>>) {

        try {

            val type = object : TypeToken<List<Reminder>>() {}.type
            val gson = Gson()
            val reminder = gson.fromJson<List<Reminder>>(json, type)
            mCategory.value = reminder

        } catch (e: JsonSyntaxException) {

            e.printStackTrace()
        }
    }

    fun fetchLocalReminder(context: Context, url: String): LiveData<List<Reminder>> {

        val mReminder = MutableLiveData<List<Reminder>>()
        val jsonArticle = context.loadJSONFromAsset(url)!!
        parseReminder(jsonArticle, mReminder)

        return mReminder
    }
}