package fit.asta.health.auth.ui.screens

import android.content.ActivityNotFoundException
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.util.Log
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.auth.api.phone.SmsRetrieverClient
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status
import fit.asta.health.auth.ui.screens.OTPReceiver.Companion.TAG

class OTPReceiver(
    val onSuccess: (Intent?) -> Unit,
    val onFailure: () -> Unit
) : BroadcastReceiver() {

    companion object {
        const val TAG = "OTP"
    }

    private var registered = false

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d(TAG, "onReceive: Called ${intent?.action}")
        if (SmsRetriever.SMS_RETRIEVED_ACTION == intent?.action) {
            val extras: Bundle? = intent.extras
            val status: Status = extras?.get(SmsRetriever.EXTRA_STATUS) as Status
            Log.d(TAG, "onReceive: $status")
            when (status.statusCode) {
                CommonStatusCodes.SUCCESS -> {
                    val consentIntent = if (SDK_INT >= 33) {
                        extras.getParcelable(SmsRetriever.EXTRA_CONSENT_INTENT, Intent::class.java)
                    } else {
                        extras.getParcelable(SmsRetriever.EXTRA_CONSENT_INTENT)
                    }

                    try {
                        onSuccess(consentIntent)
                    } catch (e: ActivityNotFoundException) {
                        // Handle the exception ...
                    }
                }

                CommonStatusCodes.TIMEOUT -> {
                    onFailure()
                }
            }
        }
    }

    fun register(context: Context) {
        if (!registered) {
            val filter = IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)
            context.registerReceiver(this, filter)
            registered = true
        }
    }

    fun unregister(context: Context) {
        if (registered) {
            context.unregisterReceiver(this)
            registered = false
        }
    }
}

fun startSMSRetrieverClient(context: Context) {
    val client: SmsRetrieverClient = SmsRetriever.getClient(context)
    val task = client.startSmsUserConsent(null)
    task.addOnSuccessListener { aVoid ->
        Log.d(TAG, "startSMSRetrieverClient addOnSuccessListener")
    }
    task.addOnFailureListener { e ->
        Log.e(TAG, "startSMSRetrieverClient addOnFailureListener" + e.stackTrace)
    }
}