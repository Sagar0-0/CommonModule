package fit.asta.health.scheduler.view.alarmsetting

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import fit.asta.health.R
import fit.asta.health.databinding.ActivityTimeSettingBinding
import fit.asta.health.scheduler.model.db.entity.AlarmEntity
import fit.asta.health.scheduler.model.net.scheduler.Adv
import fit.asta.health.scheduler.model.net.scheduler.Ivl
import fit.asta.health.scheduler.model.net.scheduler.Rep
import fit.asta.health.scheduler.model.net.scheduler.Stat
import fit.asta.health.scheduler.view.adapters.VariantIntervalAdapter
import fit.asta.health.scheduler.view.alarmsetting.dialogs.AddVariantIntervalDialog
import fit.asta.health.scheduler.view.interfaces.DialogInterface
import fit.asta.health.scheduler.view.interfaces.TimeActivityBottomSheetListener
import fit.asta.health.scheduler.viewmodel.TimeSettingViewModel

@AndroidEntryPoint
class TimeSettingActivity : AppCompatActivity(), DialogInterface, TimeActivityBottomSheetListener {

    private lateinit var binding: ActivityTimeSettingBinding
    private lateinit var viewModel: TimeSettingViewModel
    private var listOfVariantIntervals: MutableList<Stat> =
        emptyList<Stat>().toMutableList()
    private var listOfStaticIntervals: MutableList<Stat> =
        emptyList<Stat>().toMutableList()
    private lateinit var adapter: VariantIntervalAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTimeSettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[TimeSettingViewModel::class.java]

        if (intent.getParcelableExtra<AlarmEntity>("alarm_item") != null) {
            binding.alarmItem = intent.getParcelableExtra<AlarmEntity>("alarm_item")
            if (binding.alarmItem?.interval!!.isVariantInterval) {
                listOfVariantIntervals =
                    binding.alarmItem?.interval!!.variantIntervals as MutableList<Stat>
                listOfStaticIntervals =
                    binding.alarmItem?.interval!!.staticIntervals as MutableList<Stat>
            }
            Log.d("TAGTAGTAG111", "onCreate: ${binding.alarmItem}")
        }

        binding.viewModel = viewModel
        binding.listener = this
        binding.selectedInterval.setTag(R.id.repeatTimeTag, 1)
        binding.selectedInterval.setTag(R.id.repeatTimeUnitTag, "Hour")

        adapter = VariantIntervalAdapter(listOfVariantIntervals, this)
        binding.customIntervalRecycleView.adapter = adapter
        binding.customIntervalRecycleView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        binding.switchIntervalStatus.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked) {
                binding.intervalSettingContainer.visibility = VISIBLE
                binding.labelIntervalDetails.visibility = GONE
            } else {
                binding.intervalSettingContainer.visibility = GONE
                binding.labelIntervalDetails.visibility = VISIBLE
            }
        }

        binding.switchIntervalStatus.isChecked =
            binding.alarmItem?.interval?.status!!

        binding.switchVariantInterval.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked) {
                binding.customIntervalsContainer.visibility = VISIBLE
                binding.alarmIntervalRepeatContainer.visibility = GONE
            } else {
                binding.customIntervalsContainer.visibility = GONE
                binding.alarmIntervalRepeatContainer.visibility = VISIBLE
            }
        }

        binding.addCustomIntervalFAB.setOnClickListener {
            val dialog = AddVariantIntervalDialog(this, this)
            dialog.show()
        }

        binding.cancelButton.setOnClickListener {
            finish()
        }

        binding.saveButton.setOnClickListener {
            if (binding.switchVariantInterval.isChecked) {
                if (listOfVariantIntervals.isEmpty()) {
                    Toast.makeText(
                        this,
                        "At least one variant should be added inorder to save details!",
                        Toast.LENGTH_LONG
                    ).show()
                    return@setOnClickListener
                }
            }
            val intent = Intent()
            val intervalData = Ivl(
                Adv(
                    binding.switchAdvancedRemainder.isChecked,
                    binding.selectedAdvancedReminderDuration.tag.toString().toInt()
                ),
                binding.selectedTotalDuration.tag.toString().toInt(),
                binding.switchRemindAtTheEnd.isChecked,
                Rep(
                    binding.selectedInterval.getTag(R.id.repeatTimeTag).toString().toInt(),
                    binding.selectedInterval.getTag(R.id.repeatTimeUnitTag).toString()
                ),
                binding.selectedSnoozeDuration.tag.toString().toInt(),
                listOfStaticIntervals,
                binding.switchIntervalStatus.isChecked,
                listOfVariantIntervals,
                binding.switchVariantInterval.isChecked,
            )
            intent.putExtra("INTERVAL_DATA", intervalData)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    override fun sendVariantIntervalData(alarmTimeItem: Stat) {
        listOfVariantIntervals.add(alarmTimeItem)
        adapter.setData(listOfVariantIntervals)
        adapter.notifyDataSetChanged()
        Log.d("TAGTAGTAG", "sendVariantIntervalData: $listOfVariantIntervals")
    }

    override fun deleteVariantIntervalData(alarmTimeItem: Stat) {
        binding.alarmItem?.cancelInterval(this, alarmTimeItem)
        listOfVariantIntervals.remove(alarmTimeItem)
        adapter.setData(listOfVariantIntervals)
        adapter.notifyDataSetChanged()
        Log.d("TAGTAGTAG", "sendVariantIntervalData: $listOfVariantIntervals")
    }

    override fun setSnoozeTime(time: Int) {
        binding.selectedSnoozeDuration.text = "$time Minutes"
        binding.selectedSnoozeDuration.tag = time
        binding.alarmItem?.interval?.snoozeTime = time
    }

    override fun setAdvancedReminderTime(time: Int) {
        binding.selectedAdvancedReminderDuration.text = "$time Minutes"
        binding.selectedAdvancedReminderDuration.tag = time
        binding.alarmItem?.interval?.advancedReminder?.time = time
    }

    override fun setDurationTime(time: Int) {
        binding.selectedTotalDuration.text = "$time Minutes"
        binding.selectedTotalDuration.tag = time
        binding.alarmItem?.interval?.duration = time
    }

    override fun setRepeatTime(repeatItem: Rep) {
        binding.selectedInterval.text = "${repeatItem.time} ${repeatItem.unit}"
        binding.selectedInterval.setTag(R.id.repeatTimeTag, repeatItem.time)
        binding.selectedInterval.setTag(R.id.repeatTimeUnitTag, repeatItem.unit)
        binding.alarmItem?.interval?.repeatableInterval =
            Rep(repeatItem.time, repeatItem.unit)
    }
}