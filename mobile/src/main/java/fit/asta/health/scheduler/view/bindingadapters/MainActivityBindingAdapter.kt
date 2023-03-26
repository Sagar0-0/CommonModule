package fit.asta.health.scheduler.view.bindingadapters

import android.content.Intent
import android.graphics.Canvas
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.switchmaterial.SwitchMaterial
import fit.asta.health.R
import fit.asta.health.common.utils.NetworkResult
import fit.asta.health.scheduler.model.db.entity.AlarmEntity
import fit.asta.health.scheduler.view.adapters.AlarmAdapter
import fit.asta.health.scheduler.view.alarmsetting.AlarmSettingActivity
import fit.asta.health.scheduler.viewmodel.AlarmViewModel
import fit.asta.health.scheduler.viewmodel.SchedulerBackendViewModel
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import java.io.Serializable


class MainActivityBindingAdapter {

    companion object {

        @BindingAdapter("android:setAsDefaultToolBar")
        @JvmStatic
        fun setAsDefaultToolBar(toolbar: Toolbar, TAG: String) {
            (toolbar.context as AppCompatActivity).setSupportActionBar(toolbar)
        }

        @BindingAdapter("android:addFABListener")
        @JvmStatic
        fun addFABListener(floatingActionButton: FloatingActionButton, TAG: String) {
            floatingActionButton.setOnClickListener {
                val intent: Intent =
                    Intent(floatingActionButton.context, AlarmSettingActivity::class.java)
                floatingActionButton.context.startActivity(intent)
            }
        }

        @BindingAdapter(
            value = ["android:setAdapter", "android:setViewModel", "android:setBackendViewModel"],
            requireAll = true
        )
        @JvmStatic
        fun setupRecyclerView(
            recyclerView: RecyclerView,
            alarmAdapter: AlarmAdapter,
            viewModel: AlarmViewModel,
            backendViewModel: SchedulerBackendViewModel
        ) {
            val context = recyclerView.context
            recyclerView.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            recyclerView.adapter = alarmAdapter
            viewModel.allAlarms.observe((context as AppCompatActivity)) {
                Log.d("TAGTAGTAGTAG", "setupRecyclerView: $it")
                alarmAdapter.setData(it)
            }
            val itemTouchHelperCallback =
                object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
                    override fun onMove(
                        recyclerView: RecyclerView,
                        viewHolder: RecyclerView.ViewHolder,
                        target: RecyclerView.ViewHolder
                    ): Boolean {
                        return false
                    }

                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                        val mList = alarmAdapter.getData()
                        val deletedAlarm = mList[viewHolder.adapterPosition]
//                        var position : Int = viewHolder.adapterPosition
                        backendViewModel.deleteSchedule(deletedAlarm.idFromServer)
                        backendViewModel.deleteScheduleResponse.observe(context) { result ->
                            when (result) {
                                is NetworkResult.Success -> {
                                    if (result.data?.data?.flag!!) {
                                        if (deletedAlarm.status) deletedAlarm.cancelAlarm(
                                            context,
                                            deletedAlarm.alarmId,
                                            true
                                        )
                                        viewModel.deleteAlarm(deletedAlarm)

                                        Snackbar.make(
                                            recyclerView,
                                            "Deleted ${deletedAlarm.info.name}",
                                            Snackbar.LENGTH_LONG
                                        )
                                            .setAction("Undo") {
                                                // adding on click listener to our action of snack bar.
                                                // below line is to add our item to array healthHisList with a position.
                                                viewModel.insertAlarm(deletedAlarm)
                                            }.show()
                                    } else {
                                        Snackbar.make(
                                            recyclerView,
                                            "Error with ${deletedAlarm.info.name}",
                                            Snackbar.LENGTH_LONG
                                        ).show()
                                    }
                                }
                                is NetworkResult.Error -> {

                                }
                                is NetworkResult.Loading -> {

                                }
                            }
                        }
                    }

                    override fun onChildDraw(
                        c: Canvas,
                        recyclerView: RecyclerView,
                        viewHolder: RecyclerView.ViewHolder,
                        dX: Float,
                        dY: Float,
                        actionState: Int,
                        isCurrentlyActive: Boolean
                    ) {

                        RecyclerViewSwipeDecorator.Builder(
                            c,
                            recyclerView,
                            viewHolder,
                            dX,
                            dY,
                            actionState,
                            isCurrentlyActive
                        )
                            .addBackgroundColor(
                                ContextCompat.getColor(
                                    recyclerView.context,
                                    android.R.color.holo_red_light
                                )
                            )
                            .addActionIcon(R.drawable.ic_baseline_delete_24)
                            .create()
                            .decorate()

                        super.onChildDraw(
                            c,
                            recyclerView,
                            viewHolder,
                            dX,
                            dY,
                            actionState,
                            isCurrentlyActive
                        )
                    }

                }
            val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
            itemTouchHelper.attachToRecyclerView(recyclerView)
        }

        @BindingAdapter(
            value = ["android:setUpAlarmHour", "android:setUpAlarmMinutes"],
            requireAll = true
        )
        @JvmStatic
        fun setUpAlarmTime(
            alarmTimeLabel: TextView,
            alarmHour: String,
            alarmMinute: String,
        ) {
            alarmTimeLabel.text =
                String.format("%02d: %02d", alarmHour.toInt(), alarmMinute.toInt())
        }

        @BindingAdapter("android:isMidDay")
        @JvmStatic
        fun setUpMidDay(
            alarmMidDayLabel: TextView,
            isMidDay: Boolean
        ) {
            alarmMidDayLabel.text = if (isMidDay) "pm" else "am"
        }

        @BindingAdapter("android:onClick")
        @JvmStatic
        fun onAlarmItemClick(alarmContainer: MaterialCardView, alarmItem: AlarmEntity) {
            alarmContainer.setOnClickListener {
                val intent: Intent =
                    Intent(alarmContainer.context, AlarmSettingActivity::class.java)
                intent.putExtra("alarmItem", alarmItem as Serializable)
                alarmContainer.context.startActivity(intent)
            }
        }


        @BindingAdapter(
            value = ["android:setAlarmStatus", "android:setViewModel"],
            requireAll = true
        )
        @JvmStatic
        fun setAlarmStatus(
            switch: SwitchMaterial,
            alarmEntity: AlarmEntity?,
            viewModel: AlarmViewModel
        ) {
            if (alarmEntity != null) {
                switch.isChecked = alarmEntity.status
            }
            switch.setOnCheckedChangeListener { compoundButton, isChecked ->
                if (compoundButton.isShown || compoundButton.isPressed) {
                    if (alarmEntity != null) {
                        if (alarmEntity.status) {
                            alarmEntity.cancelAlarm(
                                switch.context.applicationContext,
                                alarmEntity.alarmId,
                                true
                            )
                        } else {
                            alarmEntity.schedule(switch.context.applicationContext)
                        }
                        viewModel.updateAlarm(alarmEntity)
                    }
                }
            }
        }
    }

}