package fit.asta.health.feature.scheduler.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import fit.asta.health.datastore.PrefManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

class ShutdownBroadcastReceiver: BroadcastReceiver() {
    @Inject
    lateinit var prefManager: PrefManager
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("missed", "shutfon: ")
        when (intent?.action) {
            Intent.ACTION_SHUTDOWN -> {
                Log.d("missed", "shutdown: ")
                goAsync {
                    val currentTime: Calendar = Calendar.getInstance()
                    prefManager.setShutdownTime(currentTime.timeInMillis)
                }
            }
        }
    }
    private fun BroadcastReceiver.goAsync(
        context: CoroutineContext = EmptyCoroutineContext,
        block: suspend CoroutineScope.() -> Unit
    ) {
        val pendingResult = goAsync()
        @OptIn(DelicateCoroutinesApi::class) // Must run globally; there's no teardown callback.
        GlobalScope.launch(context) {
            try {
                block()
            } finally {
                pendingResult.finish()
            }
        }
    }
}