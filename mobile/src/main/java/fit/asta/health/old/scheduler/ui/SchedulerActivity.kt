package fit.asta.health.old.scheduler.ui

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.CompoundButton
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.appbar.MaterialToolbar
import fit.asta.health.R
import fit.asta.health.notify.receiver.AlarmReceiver
import fit.asta.health.notify.reminder.ReminderViewModel
import fit.asta.health.notify.reminder.data.Reminder
import fit.asta.health.utils.CustomTimePicker
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*


class SchedulerActivity : AppCompatActivity() {

    companion object {

        private const val SCHEDULE_REMINDER = "0"

        fun launch(context: Context, reminder: Reminder? = null) {

            val intent = Intent(context, SchedulerActivity::class.java)
            intent.apply {
                intent.putExtra(SCHEDULE_REMINDER, reminder)
                context.startActivity(this)
            }
        }
    }

    private val btnDays = arrayOfNulls<CompoundButton>(7)
    private lateinit var viewModel: ReminderViewModel
    private var mReminder: Reminder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.schedule_activity)

        findViewById<CustomTimePicker>(R.id.timePicker).setIs24HourView(true)

        val tlbScheduler = findViewById<MaterialToolbar>(R.id.tlbScheduler)
        setSupportActionBar(tlbScheduler)
        tlbScheduler?.setNavigationOnClickListener {

            onBackPressed()
        }

        mReminder = intent.getParcelableExtra(SCHEDULE_REMINDER)

        viewModel = ViewModelProvider(this).get(ReminderViewModel::class.java)

        findViewById<CustomTimePicker>(R.id.btnAlarmDone).setOnClickListener {

            val reminder = buildReminder()
            if (mReminder != null) {

                updateReminder(reminder)
            } else {

                createReminder(reminder)
            }

            finish()
        }

        buildWeekDays()
        initializeTimePicker(mReminder)
    }

    @Suppress("UNUSED_PARAMETER")
    private fun buildWeekDays() {

        // Build buttons for each day.
        val sWeekdays = resources.getStringArray(R.array.days)
        val weekDays = findViewById<LinearLayout>(R.id.weekDays)
        weekDays.removeAllViews()
        val inflater = LayoutInflater.from(this)
        for (i in 0..6) {

            val btnDayFrame = inflater.inflate(R.layout.schedule_weekday, weekDays, false)
            val btnDay = btnDayFrame.findViewById<View>(R.id.btnDay) as CompoundButton
            btnDay.isChecked =
                if (mReminder == null) true else mReminder?.days?.any { it == i + 1 } ?: false
            btnDay.text = sWeekdays[i].substring(0, 1)
            weekDays.addView(btnDayFrame)
            btnDays[i] = btnDay
        }

        // Day button handlers
        /*for (i in btnDays.indices) {

            btnDays[i]!!.setOnClickListener { view ->

                //val isChecked = (view as CompoundButton).isChecked
            }
        }*/

    }

    private fun initializeTimePicker(reminder: Reminder?) {

        if (reminder != null) {

            val timePicker = findViewById<CustomTimePicker>(R.id.timePicker)
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {

                timePicker.hour = reminder.hour
                timePicker.minute = reminder.minute
            } else {

                timePicker.currentHour = reminder.hour
                timePicker.currentMinute = reminder.minute
            }
        }
    }

    private fun getCheckedDays(): List<Int> {

        val iWeekDays = arrayListOf<Int>()
        for (i in btnDays.indices) {

            val btn = btnDays[i]!!
            if (btn.isChecked) {

                iWeekDays.add(i + 1)
            }
        }

        return iWeekDays
    }

    private fun scheduleAlarm(hour: Int, min: Int) {

        /*val local: LocalDateTime = LocalDateTime.of(year, month, day, timePicker.hour, timePicker.minute)
        local.atZone(ZoneId.ofOffset("UTC", ZoneOffset.UTC)).toInstant().toEpochMilli()*/

        val calendar: Calendar = Calendar.getInstance()
        calendar.set(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH),
            hour,
            min,
            0
        )

        setAlarm(calendar.timeInMillis)
    }

    private fun setAlarm(time: Long) {

        val am = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val i = Intent(this, AlarmReceiver::class.java)
        val pi = PendingIntent.getBroadcast(this, 0, i, 0)
        am.setRepeating(AlarmManager.RTC, time, AlarmManager.INTERVAL_DAY, pi)
    }

    private fun showTimePicker(is24HourView: Boolean) {

        val cal = Calendar.getInstance()
        val dialog = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { _, _, _ ->

        }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), is24HourView)

        dialog.show()
    }

    private fun buildReminder(): Reminder {

        val hour: Int
        val min: Int

        val timePicker = findViewById<CustomTimePicker>(R.id.timePicker)
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {

            hour = timePicker.hour
            min = timePicker.minute
        } else {

            hour = timePicker.currentHour
            min = timePicker.currentMinute
        }

        return Reminder(
            id = if (mReminder != null) mReminder?.id!! else 0,
            type = Reminder.OTHER,
            title = "Workout",
            desc = "Immunity Booster",
            hour = hour,
            minute = min,
            days = getCheckedDays()
        )
    }

    private fun createReminder(reminder: Reminder) {

        GlobalScope.launch {

            viewModel.add(reminder) { success ->

                if (success) {

                    scheduleAlarm(reminder.hour, reminder.minute)
                }
            }
        }
    }

    private fun updateReminder(reminder: Reminder) {

        GlobalScope.launch {

            viewModel.update(reminder) { success ->

                if (success) {

                    rescheduleAlarm(reminder)
                }
            }
        }
    }

    private fun rescheduleAlarm(reminder: Reminder) {

        removeAlarmReminder(mReminder)
        scheduleAlarm(reminder.hour, reminder.minute)
    }

    @Suppress("UNUSED_PARAMETER")
    private fun removeAlarmReminder(oldReminder: Reminder?) {

    }
}