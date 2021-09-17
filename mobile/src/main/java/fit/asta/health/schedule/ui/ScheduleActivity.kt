package fit.asta.health.schedule.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import fit.asta.health.ActivityLauncher
import fit.asta.health.notify.reminder.data.Reminder
import fit.asta.health.schedule.tags.data.ScheduleTagData
import fit.asta.health.schedule.tags.ui.TagsActivity
import fit.asta.health.schedule.viewmodel.ScheduleViewModel
import kotlinx.android.synthetic.main.activity_scheduler.*
import kotlinx.android.synthetic.main.card_schedule_clock.*
import kotlinx.android.synthetic.main.card_schedule_tag.*
import org.koin.android.ext.android.inject


class ScheduleActivity : AppCompatActivity() {

    private val viewSchedule: ScheduleView by inject()
    private val viewModelSchedule: ScheduleViewModel by inject()
    private val launcher: ActivityLauncher by inject()

    companion object {

        private const val SCHEDULE_REMINDER = "0"

        fun launch(context: Context, reminder: Reminder? = null) {
            val intent = Intent(context, ScheduleActivity::class.java)
            intent.apply {
                intent.putExtra(SCHEDULE_REMINDER, reminder)
                context.startActivity(this)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewSchedule.setContentView(this))
        //setSupportActionBar(tlbScheduler)

        viewModelSchedule.observerScheduleLiveData(this, ScheduleObserver(viewSchedule))

        viewSchedule.submitClickListener(SubmitClickListenerImpl(viewModelSchedule))
        tlbScheduler.setNavigationOnClickListener {
            onBackPressed()
        }

        extended_fab.setOnClickListener {
            viewSchedule.captureTime { time ->
                viewModelSchedule.addTime(time)
            }
        }

        textTagsView.setOnClickListener {
            //launcher.launchTagsActivity(this, viewModelSchedule.getTagId())
            launchTagActivity(viewModelSchedule.getTagId())
        }
    }

    private val tagActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val selectedData =
                    data?.getParcelableExtra<ScheduleTagData>(TagsActivity.TAG_SELECTED)
                viewModelSchedule.updateTag(selectedData)
            }
        }

    private fun launchTagActivity(selectedTagId: String?) {
        val intent = Intent(this, TagsActivity::class.java)
        intent.putExtra(TagsActivity.TAG_SELECTED, selectedTagId)
        tagActivity.launch(intent)
    }
}