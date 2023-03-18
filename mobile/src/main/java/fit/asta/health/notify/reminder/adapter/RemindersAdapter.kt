package fit.asta.health.notify.reminder.adapter


import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import fit.asta.health.ActivityLauncher
import fit.asta.health.R
import fit.asta.health.notify.reminder.data.Reminder
import fit.asta.health.utils.GenericAdapter
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class RemindersAdapter(
    val context: Context,
    val list: List<Reminder>,
    val deleteCallback: (Reminder) -> Unit
) :
    GenericAdapter<Reminder>(list) {

    @Inject
    lateinit var launcher: ActivityLauncher
    private val dateFormat = SimpleDateFormat("h:mma", Locale.getDefault())

    fun delete(reminder: Reminder) {

        deleteCallback(reminder)
    }

    override fun setViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.scheduler_reminder_card, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindData(holder: RecyclerView.ViewHolder, item: Reminder, pos: Int) {

        val itemHolder = holder as ItemViewHolder
        itemHolder.setData(item)
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun setData(reminder: Reminder) {

            itemView.findViewById<AppCompatTextView>(R.id.txtRemindTitle).text = reminder.title
            itemView.findViewById<AppCompatTextView>(R.id.txtRemindDesc).text = reminder.desc
            itemView.findViewById<AppCompatTextView>(R.id.txtRemindTime).text = getTime(reminder)
            itemView.findViewById<AppCompatTextView>(R.id.txtRemindDays).text = getDays(reminder)
            itemView.findViewById<AppCompatImageView>(R.id.imgRemindType)
                .setImageDrawable(selectImage(reminder))

            itemView.setOnClickListener {

                launcher.launchSchedulerActivity(it.context, reminder)
            }

            itemView.findViewById<AppCompatTextView>(R.id.btnRemindDelete).setOnClickListener {

                delete(reminder)
            }
        }

        private fun getDays(reminder: Reminder): String {

            var txtDays = ""
            val days = reminder.days

            when {
                days == null || days.isEmpty() -> {

                    txtDays = context.resources.getString(R.string.one_time)
                }
                days.size == 7 -> {

                    txtDays = context.resources.getString(R.string.every_day)
                }
                else -> {

                    val sWeekdays = context.resources.getStringArray(R.array.days)
                    for (day in days) {

                        txtDays += sWeekdays[day - 1] + "  "
                    }

                    txtDays.trim()
                }
            }

            return txtDays
        }

        private fun getTime(reminder: Reminder): String {

            val date = Calendar.getInstance()
            date.set(Calendar.HOUR_OF_DAY, reminder.hour)
            date.set(Calendar.MINUTE, reminder.minute)

            return dateFormat.format(date.time).lowercase(Locale.getDefault())
        }

        private fun selectImage(reminder: Reminder): Drawable? {

            val ctx = itemView.findViewById<AppCompatTextView>(R.id.imgRemindType).context
            return when (reminder.type) {
                Reminder.EXERCISE -> ContextCompat.getDrawable(ctx, R.drawable.ic_yoga)
                Reminder.WATER -> ContextCompat.getDrawable(ctx, R.drawable.ic_water)
                else -> ContextCompat.getDrawable(ctx, R.drawable.ic_person)
            }
        }
    }
}