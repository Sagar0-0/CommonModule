package fit.asta.health.feature.scheduler.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import fit.asta.health.feature.scheduler.util.ToolsStateManager
import fit.asta.health.resources.drawables.R
import javax.inject.Inject

class ToolsBroadcastReceiver :BroadcastReceiver() {

    @Inject
    lateinit var toolsStateManager: ToolsStateManager

    // inject required dao impl ,pref managers in tool state manager and handle all jobs in there
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("hello", "onReceive: ")
        //
        when(intent?.action){
            ToolNotificationHelper.ACTION_ADD_WATER->{
                ToolNotificationHelper.listener.updateTextView(R.id.tv_content,"WATER ADDED")
            }

            ToolNotificationHelper.ACTION_ADD_JUICE->{
                ToolNotificationHelper.listener.updateTextView(R.id.tv_content,"JUICE ADDED")
            }
        }
    }
}