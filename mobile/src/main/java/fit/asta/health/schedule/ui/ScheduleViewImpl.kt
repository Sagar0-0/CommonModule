package fit.asta.health.schedule.ui

import android.app.Activity
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.widget.CompoundButton
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import fit.asta.health.R
import fit.asta.health.notify.reminder.data.Reminder
import fit.asta.health.schedule.adapter.TimeCyclesAdapter
import fit.asta.health.schedule.data.ScheduleTimeData
import fit.asta.health.schedule.tags.data.ScheduleTagData
import kotlinx.android.synthetic.main.schedule_clock_card.view.*
import kotlinx.android.synthetic.main.schedule_everyday_card.view.*
import kotlinx.android.synthetic.main.schedule_tag_card.view.*
import kotlinx.android.synthetic.main.schedule_activity.view.*


class ScheduleViewImpl : ScheduleView {

    private var rootView: View? = null
    private var tagsView: View? = null
    private val btnDays = arrayOfNulls<CompoundButton>(7)
    private var mReminder: Reminder? = null

    /*private var beforeReminderView: View? = null
    private lateinit var viewManager: GridLayoutManager
    private lateinit var recyclerView: RecyclerView
    private lateinit var addButton: ExtendedFloatingActionButton
    private lateinit var titleEditText: TextInputEditText
    private lateinit var descriptionEditText: TextInputEditText
    private lateinit var radioGroup: RadioGroup
    private lateinit var setCheckBoxMode: CheckBox*/

    override fun setContentView(activity: Activity): View? {

        rootView = LayoutInflater.from(activity).inflate(R.layout.schedule_activity, null, false)
        buildWeekDays()
        setupTimeCycleRecyclerView()
        /*beforeReminderView =
            LayoutInflater.from(activity).inflate(R.layout.reminder_bottomsheet, null, false)
        viewManager = GridLayoutManager(activity, 3)*/

        return rootView
    }

    override fun changeStateSchedule(state: ScheduleView.State) {
        when (state) {
            is ScheduleView.State.LoadScheduleAction -> setAdapterTimeCycles(state.schedule.sessions[0].cycles)
            is ScheduleView.State.LoadTagAction -> setScheduleTag(state.tag)
            is ScheduleView.State.Error -> showError(state.message)
            ScheduleView.State.Empty -> showEmpty()
        }
    }

    private fun setScheduleTag(tag: ScheduleTagData?) {
        rootView?.let {
            it.textTagsView.text = tag?.tagName
        }
    }

    private fun setAdapterTimeCycles(list: List<ScheduleTimeData>) {
        rootView?.let {
            (it.rcvTimeCycles.adapter as TimeCyclesAdapter).updateList(list)
        }
    }

    @Suppress("UNUSED_PARAMETER")
    private fun showError(message: String) {

    }

    private fun showEmpty() {
        //finish()
    }

    @Suppress("UNUSED_PARAMETER")
    private fun buildWeekDays() {

        // Build buttons for each day.
        rootView?.let { view ->
            val sWeekdays = view.resources.getStringArray(R.array.days)
            view.weekDays.removeAllViews()
            val inflater = LayoutInflater.from(view.context)
            for (i in 0..6) {

                val btnDayFrame = inflater.inflate(R.layout.schedule_weekday, view.weekDays, false)
                val btnDay = btnDayFrame.findViewById<View>(R.id.btnDay) as CompoundButton
                btnDay.isChecked =
                    if (mReminder == null) true else mReminder?.days?.any { it == i + 1 } ?: false
                btnDay.text = sWeekdays[i].substring(0, 1)
                view.weekDays.addView(btnDayFrame)
                btnDays[i] = btnDay
            }
        }

        // Day button handlers
        /*for (i in btnDays.indices) {

            btnDays[i]!!.setOnClickListener { view ->

                //val isChecked = (view as CompoundButton).isChecked
            }
        }*/
    }

    private fun setupTimeCycleRecyclerView() {
        rootView?.let {
            val adapter = TimeCyclesAdapter()
            it.rcvTimeCycles.layoutManager = LinearLayoutManager(it.context)
            it.rcvTimeCycles.adapter = adapter
        }
    }

    override fun captureTime(callBack: (time: ScheduleTimeData) -> Unit) {
        val hour: Int
        val min: Int

        rootView?.let { view ->

            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {

                hour = view.timePicker.hour
                min = view.timePicker.minute
            } else {

                hour = view.timePicker.currentHour
                min = view.timePicker.currentMinute
            }

            val time = ScheduleTimeData(hour, min)
            callBack.invoke(time)
            (view.rcvTimeCycles.adapter as TimeCyclesAdapter).addTime(time)
        }
    }

    private fun launchBottomSheet(activity: Activity, view: View) {
        val dlgBottom = BottomSheetDialog(activity)
        dlgBottom.setContentView(view)
        dlgBottom.setCancelable(true)
        dlgBottom.show()
    }

    override fun submitClickListener(listener: View.OnClickListener) {
        rootView?.let { view ->
            view.btnAlarmDone.setOnClickListener {
                listener.onClick(it)
            }
        }
    }

    /*private fun onCreateView() {

        rootView?.let { view ->

            view.timePicker.setIs24HourView(true)
            titleEditText = view.edtTitle
            descriptionEditText = view.edtDescription
            recyclerView = view.rcvTimeCycles
            addButton = view.extended_fab
            radioGroup = view.radioGroup
            //setCheckBoxMode = view.checkBox
        }

        var ed = titleEditText.text.toString()
        var edi = descriptionEditText.text.toString()

        setCheckBoxMode.setOnCheckedChangeListener { view, isChecked ->
            Toast.makeText(this, isChecked.toString(), Toast.LENGTH_LONG).show()
        }

        radioGroup.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                val radio: RadioButton = findViewById(checkedId)
                Toast.makeText(
                    applicationContext, " On checked change : ${radio.text}",
                    Toast.LENGTH_SHORT
                ).show()
            })

        initialiseAdapter()
        mReminder = intent.getParcelableExtra(ScheduleActivity.SCHEDULE_REMINDER)

        initializeTimePicker(mReminder)
    }

    private fun initializeTimePicker(reminder: Reminder?) {

        reminder?.let { rem ->
            rootView?.let { view ->
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {

                    view.timePicker.hour = rem.hour
                    view.timePicker.minute = rem.minute
                } else {

                    view.timePicker.currentHour = rem.hour
                    view.timePicker.currentMinute = rem.minute
                }
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

    private fun initialiseAdapter() {
        recyclerView.layoutManager = viewManager
        observeData()
    }

    fun observeData() {
        viewScheduleModel.lst.observe(this, Observer {
            Log.i("data", it.toString())
            recyclerView.adapter = TimeCyclesAdapter(viewScheduleModel, it, this)
        })
    }*/
}