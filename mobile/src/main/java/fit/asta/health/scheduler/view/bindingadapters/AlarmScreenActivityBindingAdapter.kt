package fit.asta.health.scheduler.view.bindingadapters

import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import fit.asta.health.scheduler.model.db.entity.AlarmEntity
import fit.asta.health.scheduler.model.net.scheduler.Stat
import fit.asta.health.scheduler.services.AlarmService
import fit.asta.health.scheduler.viewmodel.AlarmViewModel
import me.thanel.swipeactionview.SwipeActionView
import me.thanel.swipeactionview.SwipeDirection
import me.thanel.swipeactionview.SwipeGestureListener
import java.util.*

class AlarmScreenActivityBindingAdapter {

    companion object {

        @BindingAdapter(
            value = ["android:setViewModel", "android:setAlarmItem", "android:setAlarmVariantInterval"],
            requireAll = true
        )
        @JvmStatic
        fun setupSwipeView(
            swiperView: SwipeActionView,
            viewModel: AlarmViewModel,
            alarmEntity: AlarmEntity?,
            variantIntervalItem: Stat?
        ) {
            swiperView.setRippleColor(SwipeDirection.Right, Color.BLUE)
            swiperView.swipeGestureListener = object : SwipeGestureListener {

                override fun onSwipedLeft(swipeActionView: SwipeActionView): Boolean {
                    val calendar: Calendar = Calendar.getInstance()
                    calendar.timeInMillis = System.currentTimeMillis()
                    calendar.add(Calendar.MINUTE, 5)
                    Log.d(
                        "TAGTAGTAG", "onSwipedLeft::  " +
                                "${calendar.get(Calendar.HOUR_OF_DAY)} : ${calendar.get(Calendar.MINUTE)} "
                    )
                    if (alarmEntity != null) {
                        alarmEntity.time.hours =
                            if (calendar.get(Calendar.HOUR_OF_DAY) >= 12) (calendar.get(Calendar.HOUR_OF_DAY) - 12).toString() else calendar.get(
                                Calendar.HOUR_OF_DAY
                            ).toString()
                        alarmEntity.time.minutes = calendar.get(Calendar.MINUTE).toString()
                        alarmEntity.info.name = "Snooze ${alarmEntity.info.name}"
                        alarmEntity.schedule(swiperView.context.applicationContext)
                    }

                    val intentService =
                        Intent(swiperView.context.applicationContext, AlarmService::class.java)
                    swiperView.context.applicationContext.stopService(intentService)
                    (swiperView.context as AppCompatActivity).finishAndRemoveTask()
                    return true
                }

                override fun onSwipedRight(swipeActionView: SwipeActionView): Boolean {
                    if (alarmEntity != null) {
                        if (variantIntervalItem != null) {
                            alarmEntity.cancelInterval(
                                swiperView.context.applicationContext,
                                variantIntervalItem
                            )
                            if (alarmEntity.interval.isRemainderAtTheEnd) {
                                alarmEntity.schedulerPostNotification(
                                    swiperView.context.applicationContext,
                                    true,
                                    variantIntervalItem,
                                    variantIntervalItem.id
                                )
                            }
                        } else {
                            alarmEntity.cancelAlarm(
                                swiperView.context.applicationContext,
                                alarmEntity.alarmId,
                                false
                            )
                            if (alarmEntity.week.recurring) {
                                viewModel.updateAlarm(
                                    alarmEntity.copy(status = true)
                                )
                                alarmEntity.schedule(swiperView.context.applicationContext)
                            } else {
                                viewModel.updateAlarm(
                                    alarmEntity.copy(status = false)
                                )
                            }
                            if (alarmEntity.interval.isRemainderAtTheEnd) {
                                alarmEntity.schedulerPostNotification(
                                    swiperView.context.applicationContext,
                                    false,
                                    null,
                                    alarmEntity.alarmId
                                )
                            }
                        }
                        Log.d("TAGTAGTAG", "onSwipedRight: ")
                    }
                    val intentService =
                        Intent(swiperView.context.applicationContext, AlarmService::class.java)
                    swiperView.context.applicationContext.stopService(intentService)
                    (swiperView.context as AppCompatActivity).finishAndRemoveTask()
                    return true
                }
            }
        }

        @BindingAdapter("android:setAlarmItem")
        @JvmStatic
        fun setupAlarmActivityTime(textView: TextView, alarmEntity: AlarmEntity?) {
            if (alarmEntity?.time?.midDay == true) {
                textView.text = String.format(
                    Locale.getDefault(),
                    "%02d:%02d pm",
                    alarmEntity.time.hours.toInt(),
                    alarmEntity.time.minutes.toInt()
                )
            } else {
                textView.text = String.format(
                    Locale.getDefault(),
                    "%02d:%02d am",
                    alarmEntity?.time?.hours?.toInt(),
                    alarmEntity?.time?.minutes?.toInt()
                )
            }
        }
    }
}