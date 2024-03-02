package fit.asta.health.feature.scheduler.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class ToolsBroadcastReceiver :BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("hello", "onReceive: ")
        when(intent?.action){
            ToolNotificationHelper.ACTION_ADD_WATER->{
                ToolNotificationHelper.listener.updateWaterTool("Hello")
            }

            ToolNotificationHelper.ACTION_ADD_JUICE->{
                ToolNotificationHelper.listener.updateWaterTool("JUICE")
            }
        }
    }
}