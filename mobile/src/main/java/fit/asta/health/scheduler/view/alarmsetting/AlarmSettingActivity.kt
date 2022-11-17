package fit.asta.health.scheduler.view.alarmsetting

import android.app.Activity
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import fit.asta.health.R
import fit.asta.health.databinding.ActivityAlarmSettingBinding
import fit.asta.health.scheduler.model.db.entity.AlarmEntity
import fit.asta.health.scheduler.model.db.entity.TagEntity
import fit.asta.health.scheduler.model.net.scheduler.*
import fit.asta.health.scheduler.util.Constants
import fit.asta.health.scheduler.view.interfaces.BottomSheetInterface
import fit.asta.health.scheduler.viewmodel.AlarmViewModel
import fit.asta.health.scheduler.viewmodel.SchedulerBackendViewModel
import java.io.Serializable

@AndroidEntryPoint
class AlarmSettingActivity : AppCompatActivity(), BottomSheetInterface {

    private lateinit var _binding: ActivityAlarmSettingBinding
    private var alarmEntity: AlarmEntity? = null
    private lateinit var alarmViewModel: AlarmViewModel
    private lateinit var backendViewModel: SchedulerBackendViewModel

    var resultLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                val data: Intent? = result.data
                Log.d(
                    "TAGTAGTAG",
                    "setupIntervalScreenDisplayListener: ${data?.getParcelableExtra<Ivl>("INTERVAL_DATA")}"
                )
                _binding.alarmItem?.interval =
                    data?.getParcelableExtra<Ivl>("INTERVAL_DATA")!!
            }
        }

    var resultLauncherForTags =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                val data: Intent? = result.data
                Log.d(
                    "TAGTAGTAG",
                    "resultLauncherForTags: ${data?.getParcelableExtra<TagEntity>("TAG_ENTITY")}"
                )
                val tag = data?.getParcelableExtra<TagEntity>("TAG_ENTITY")!!
                _binding.selectedTag.text = tag.meta.name
                _binding.selectedTag.setTag(R.id.tagMeta, tag)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAlarmSettingBinding.inflate(layoutInflater)
        setContentView(_binding.root)
//        _binding.resultLauncher = resultLauncher

        alarmViewModel = ViewModelProvider(this)[AlarmViewModel::class.java]
        backendViewModel = ViewModelProvider(this)[SchedulerBackendViewModel::class.java]

        val alarmTone: Uri = RingtoneManager.getActualDefaultRingtoneUri(
            this,
            RingtoneManager.TYPE_ALARM
        )

        if (intent.getParcelableExtra<AlarmEntity>("alarmItem") != null) {
            alarmEntity = intent.getParcelableExtra("alarmItem")
            _binding.alarmItem = alarmEntity
        } else {
            _binding.alarmItem = AlarmEntity(
                status = false,
                week = Wk(
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false
                ),
                time = Time(
                    hours = if (_binding.timepicker.currentHour >= 12) "${_binding.timepicker.currentHour - 12}" else "${_binding.timepicker.currentHour}",
                    minutes = "${_binding.timepicker.currentMinute}",
                    midDay = _binding.timepicker.currentHour >= 12,
                ),
                info = Info(
                    description = "description",
                    name = "Label",
                    tag = "Nap",
                    tagId = "1",
                    url = "url"
                ),
                interval = Ivl(
                    advancedReminder = Adv(
                        false, 15
                    ),
                    duration = 30,
                    isRemainderAtTheEnd = false,
                    repeatableInterval = Rep(
                        1, "Hour"
                    ),
                    snoozeTime = 5,
                    staticIntervals = emptyList(),
                    status = false,
                    variantIntervals = ArrayList<Stat>(),
                    isVariantInterval = false
                ),

                mode = "Notification",
                important = false,
                vibration = Vib(
                    "50",
                    false
                ),
                tone = Tone(
                    "Default",
                    1,
                    alarmTone.toString()
                ),
                idFromServer = "",
                userId = Constants.USER_ID,
                meta = Meta(
                    cBy = 1,
                    cDate = "1",
                    sync = 1,
                    uDate = "1"
                )
            )
        }
        Log.d("TAGTAGTAG_SETTING", "onCreate: ${_binding.alarmItem}")
        _binding.viewModel = alarmViewModel
        _binding.backendViewModel = backendViewModel
        _binding.listener = this

        _binding.alarmIntervalContainer.setOnClickListener {
            val intent = Intent(this, TimeSettingActivity::class.java)
            if (_binding.alarmItem != null) intent.putExtra(
                "alarm_item",
                _binding.alarmItem as Serializable
            )
//                container.context.startActivity(intent)
            resultLauncher.launch(intent)
        }

        _binding.alarmTagContainer.setOnClickListener {
            val intent = Intent(this, TagsActivity::class.java)
            if (_binding.alarmItem != null) intent.putExtra(
                "alarm_item",
                _binding.alarmItem as Serializable
            )
//                container.context.startActivity(intent)
            resultLauncherForTags.launch(intent)
        }

    }

    override fun onAlarmNameListener(alarmTitle: String) {
        _binding.selectedLabel.text = alarmTitle
        _binding.selectedLabel.tag = alarmTitle
    }

    override fun onAlarmDescriptionListener(alarmDescription: String) {
        _binding.selectedDescription.text = alarmDescription
        _binding.selectedDescription.tag = alarmDescription
    }

    override fun onAlarmReminderModeListener(alarmReminderMode: String) {
        _binding.selectedMode.text = alarmReminderMode
        _binding.selectedMode.tag = alarmReminderMode
    }

    override fun onAlarmVibrationModeListener(alarmVibrationPoints: String) {
        _binding.selectedVibrationPoint.text = "$alarmVibrationPoints %"
        _binding.selectedVibrationPoint.tag = alarmVibrationPoints
    }

    override fun onAlarmWeekListener(alarmWeek: Wk) {
        _binding.selectedDays.text = Constants.getRecurringDaysText(alarmWeek)
        _binding.selectedDays.tag = Constants.getRecurringDaysText(alarmWeek)
        Log.d("TAGTAGTAG", "onAlarmWeekListener: " + Constants.getRecurringDaysText(alarmWeek))
    }
}