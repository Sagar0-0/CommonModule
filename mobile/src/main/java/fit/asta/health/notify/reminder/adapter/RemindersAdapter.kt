package fit.asta.health.notify.reminder.adapter


import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import fit.asta.health.ActivityLauncher
import fit.asta.health.R
import fit.asta.health.notify.reminder.data.Reminder
import fit.asta.health.utils.GenericAdapter
import kotlinx.android.synthetic.main.list_item_reminder.view.*
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.text.SimpleDateFormat
import java.util.*

class RemindersAdapter(
    val context: Context,
    val list: List<Reminder>,
    val deleteCallback: (Reminder) -> Unit
) :
    GenericAdapter<Reminder>(list), KoinComponent {

    private val launcher: ActivityLauncher by inject()
    private val dateFormat = SimpleDateFormat("h:mma", Locale.getDefault())

    fun delete(reminder: Reminder) {

        deleteCallback(reminder)
    }

    override fun setViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_reminder, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindData(holder: RecyclerView.ViewHolder, item: Reminder, pos: Int) {

        val itemHolder = holder as ItemViewHolder
        itemHolder.setData(item)
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun setData(reminder: Reminder) {

            itemView.txtRemindTitle.text = reminder.title
            itemView.txtRemindDesc.text = reminder.desc
            itemView.txtRemindTime.text = getTime(reminder)
            itemView.txtRemindDays.text = getDays(reminder)
            itemView.imgRemindType.setImageDrawable(selectImage(reminder))

            itemView.setOnClickListener {

                launcher.launchSchedulerActivity(it.context, reminder)
            }

            itemView.btnRemindDelete.setOnClickListener {

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

            return dateFormat.format(date.time).toLowerCase(Locale.getDefault())
        }

        private fun selectImage(reminder: Reminder): Drawable? {

            return when (reminder.type) {

                Reminder.EXERCISE -> ContextCompat.getDrawable(
                    itemView.imgRemindType.context,
                    R.drawable.ic_yoga
                )
                Reminder.WATER -> ContextCompat.getDrawable(
                    itemView.imgRemindType.context,
                    R.drawable.ic_water
                )
                else -> ContextCompat.getDrawable(
                    itemView.imgRemindType.context,
                    R.drawable.ic_person
                )
            }
        }
    }
}