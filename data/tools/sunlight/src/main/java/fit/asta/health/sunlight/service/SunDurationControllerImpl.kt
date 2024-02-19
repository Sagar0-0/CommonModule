package fit.asta.health.sunlight.service

import android.os.CountDownTimer
import android.util.Log
import fit.asta.health.sunlight.remote.model.SunDurationProgress
import fit.asta.health.sunlight.remote.model.SunDurationState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SunDurationControllerImpl @Inject constructor() : SunDurationController {
    var minutesPassed=0
    var target=0
    var onMinutesPassed:((Int)->Unit)={

    }
    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.Default + job)
    private  fun startCoroutineTimer(delayMillis: Long = target.toLong(), repeatMillis: Long, action: () -> Unit) = scope.launch(Dispatchers.IO) {
        delay(delayMillis)
        if (repeatMillis > 0) {
            while (true) {
                action()
                delay(repeatMillis)
            }
        } else {
            action()
        }
    }

    private val timer: Job = startCoroutineTimer(delayMillis = 1000, repeatMillis = 1000) {
        onMinutesPassed.invoke(minutesPassed++)
    }

    fun startTimer(target: Int) {
        this.target=target
        timer.start()
    }

    fun cancelTimer() {
        timer.cancel()
    }
}

class CounterTimer{
    private val totalTimeInMillis: Long = 0
    private val intervalInMillis: Long = 0
    private var countDownTimer: CountDownTimer? = null
    private var isTimerRunning = false
    private var timeRemainingInMillis = totalTimeInMillis
    lateinit var  listener: TimerListener

    interface TimerListener {
        fun onTimerTick(timeRemainingInMillis: Long)
        fun onTimerFinish()
    }
    fun start() {
        if (!isTimerRunning) {
            countDownTimer = object : CountDownTimer(timeRemainingInMillis, intervalInMillis) {
                override fun onTick(millisUntilFinished: Long) {
                    timeRemainingInMillis = millisUntilFinished
                    listener.onTimerTick(millisUntilFinished)
                }
                override fun onFinish() {
                    isTimerRunning = false
                    listener.onTimerFinish()
                }
            }.start()

            isTimerRunning = true
        }
    }

    fun pause() {
        countDownTimer?.cancel()
        isTimerRunning = false
    }

    fun reset() {
        countDownTimer?.cancel()
        isTimerRunning = false
        timeRemainingInMillis = totalTimeInMillis
        listener.onTimerTick(timeRemainingInMillis)
    }

    fun isRunning(): Boolean {
        return isTimerRunning
    }

    fun getTimeRemainingInMillis(): Long {
        return timeRemainingInMillis
    }
}
