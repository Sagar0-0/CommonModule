/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package fit.asta.health.scheduler.ref.alarms

import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.ServiceConnection
import android.media.AudioManager
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import fit.asta.health.scheduler.ref.LogUtils
import fit.asta.health.scheduler.ref.Utils
import fit.asta.health.scheduler.ref.db.AlarmInstanceDao
import fit.asta.health.scheduler.ref.provider.AlarmInstance
import fit.asta.health.scheduler.ref.provider.ClockContract.InstancesColumns
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AlarmActivity : AppCompatActivity() {

    @Inject
    lateinit var alarmInstanceDao: AlarmInstanceDao
    val scope = CoroutineScope(Dispatchers.Main)

    val alarmStateManager = AlarmStateManager()
    private val mReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            val action: String? = intent.action
            LOGGER.v("Received broadcast: %s", action)

            if (!mAlarmHandled) {
                when (action) {
                    AlarmService.ALARM_SNOOZE_ACTION -> snooze()
                    AlarmService.ALARM_DISMISS_ACTION -> dismiss()
                    AlarmService.ALARM_DONE_ACTION -> finish()
                    else -> LOGGER.i("Unknown broadcast: %s", action)
                }
            } else {
                LOGGER.v("Ignored broadcast: %s", action)
            }
        }
    }

    private val mConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            LOGGER.i("Finished binding to AlarmService")
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            LOGGER.i("Disconnected from AlarmService")
        }
    }

    private var mAlarmInstance: AlarmInstance? = null
    private var mAlarmHandled = false
    private var mReceiverRegistered = false

    /** Whether the AlarmService is currently bound  */
    private var mServiceBound = false

    @RequiresApi(Build.VERSION_CODES.O_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        volumeControlStream = AudioManager.STREAM_ALARM
        val instanceId = intent.getLongExtra("id", -1)
        scope.launch {
            mAlarmInstance = alarmInstanceDao.getInstance(instanceId)
        }
        if (mAlarmInstance == null) {
            // The alarm was deleted before the activity got created, so just finish()
            LOGGER.e("Error displaying alarm for intent: %s", intent)
            finish()
            return
        } else if (mAlarmInstance!!.mAlarmState != InstancesColumns.FIRED_STATE) {
            LOGGER.i("Skip displaying alarm for instance: %s", mAlarmInstance)
            finish()
            return
        }

        LOGGER.i("Displaying alarm for instance: %s", mAlarmInstance)

        if (Utils.isOOrLater) {
            setShowWhenLocked(true)
            setTurnScreenOn(true)
            window.addFlags(
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                        or WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON
            )
        } else {
            window.addFlags(
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                        or WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                        or WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                        or WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                        or WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON
            )
        }

        // Close dialogs and window shade, so this is fully visible
        sendBroadcast(Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS))

    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()

        // Re-query for AlarmInstance in case the state has changed externally
        val instanceId = intent.getLongExtra("id", -1)
        scope.launch {
            mAlarmInstance = alarmInstanceDao.getInstance(instanceId)
        }

        if (mAlarmInstance == null) {
            LOGGER.i("No alarm instance for instanceId: %d", instanceId)
            finish()
            return
        }

        // Verify that the alarm is still firing before showing the activity
        if (mAlarmInstance!!.mAlarmState != InstancesColumns.FIRED_STATE) {
            LOGGER.i("Skip displaying alarm for instance: %s", mAlarmInstance)
            finish()
            return
        }

        if (!mReceiverRegistered) {
            // Register to get the alarm done/snooze/dismiss intent.
            val filter = IntentFilter(AlarmService.ALARM_DONE_ACTION)
            filter.addAction(AlarmService.ALARM_SNOOZE_ACTION)
            filter.addAction(AlarmService.ALARM_DISMISS_ACTION)
            registerReceiver(mReceiver, filter, Context.RECEIVER_EXPORTED)
            mReceiverRegistered = true
        }
        bindAlarmService()
    }

    override fun onPause() {
        super.onPause()
        unbindAlarmService()

        // Skip if register didn't happen to avoid IllegalArgumentException
        if (mReceiverRegistered) {
            unregisterReceiver(mReceiver)
            mReceiverRegistered = false
        }
    }


    /**
     * Perform snooze animation and send snooze intent.
     */
    private fun snooze() {
        mAlarmHandled = true
        LOGGER.v("Snoozed: %s", mAlarmInstance)
        alarmStateManager.setSnoozeState(this, mAlarmInstance!!, false /* showToast */)
        // Unbind here, otherwise alarm will keep ringing until activity finishes.
        unbindAlarmService()
    }

    /**
     * Perform dismiss animation and send dismiss intent.
     */
    private fun dismiss() {
        mAlarmHandled = true
        LOGGER.v("Dismissed: %s", mAlarmInstance)
        alarmStateManager.deleteInstanceAndUpdateParent(this, mAlarmInstance!!)
        // Unbind here, otherwise alarm will keep ringing until activity finishes.
        unbindAlarmService()
    }

    /**
     * Bind AlarmService if not yet bound.
     */
    private fun bindAlarmService() {
        if (!mServiceBound) {
            val intent = Intent(this, AlarmService::class.java)
            bindService(intent, mConnection, Context.BIND_AUTO_CREATE)
            mServiceBound = true
        }
    }

    /**
     * Unbind AlarmService if bound.
     */
    private fun unbindAlarmService() {
        if (mServiceBound) {
            unbindService(mConnection)
            mServiceBound = false
        }
    }


    companion object {
        private val LOGGER = LogUtils.Logger("AlarmActivity")
    }
}