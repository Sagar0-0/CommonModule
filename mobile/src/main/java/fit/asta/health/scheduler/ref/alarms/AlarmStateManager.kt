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
import android.content.Context
import android.content.Intent
import android.os.PowerManager
import dagger.hilt.android.AndroidEntryPoint
import fit.asta.health.scheduler.ref.AlarmAlertWakeLock
import fit.asta.health.scheduler.ref.Utils.INDICATOR_ACTION
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * This class handles all the state changes for alarm instances. You need to
 * register all alarm instances with the state manager if you want them to
 * be activated. If a major time change has occurred (ie. TIMEZONE_CHANGE, TIMESET_CHANGE),
 * then you must also re-register instances to fix their states.
 *
 * Please see [) for special transitions when major time changes][.registerInstance]
 */
@AndroidEntryPoint
class AlarmStateManager : BroadcastReceiver() {
    @Inject
    lateinit var alarmDataManager: AlarmDataManager
    val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    override fun onReceive(context: Context, intent: Intent) {
        if (INDICATOR_ACTION == intent.action) {
            return
        }

        val result: PendingResult = goAsync()
        val wl: PowerManager.WakeLock = AlarmAlertWakeLock.createPartialWakeLock(context)
        wl.acquire(10 * 60 * 1000L /*10 minutes*/)
        scope.launch {
            alarmDataManager.handleIntent(context, intent)
            result.finish()
            wl.release()
        }
    }
}