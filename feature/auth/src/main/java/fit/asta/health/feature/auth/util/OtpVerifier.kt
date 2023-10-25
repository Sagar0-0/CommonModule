package fit.asta.health.feature.auth.util

import android.app.Activity
import android.content.Context
import android.util.Log
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.auth.api.phone.SmsRetrieverClient
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.concurrent.TimeUnit

object OtpVerifier {

    fun startPhoneVerification(
        number: String, // Phone Number with country Code
        activity: Activity,
        onVerificationComplete: (PhoneAuthCredential) -> Unit,
        onVerificationFailure: (FirebaseException) -> Unit,
        onCodeSent: (String) -> Unit
    ) {

        val auth: FirebaseAuth = Firebase.auth
        val callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks =
            object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    onVerificationComplete(credential)
                }

                override fun onVerificationFailed(exception: FirebaseException) {
                    onVerificationFailure(exception)
                }

                override fun onCodeSent(
                    verificationId: String,
                    token: PhoneAuthProvider.ForceResendingToken
                ) {
                    super.onCodeSent(verificationId, token)
                    onCodeSent(verificationId)
                }
            }


        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(number)
            .setTimeout(0L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(callbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }


    fun startSMSRetrieverClient(context: Context) {
        val client: SmsRetrieverClient = SmsRetriever.getClient(context)
        val task = client.startSmsUserConsent(null)
        task.addOnSuccessListener {
            Log.d("Phone", "startSMSRetrieverClient addOnSuccessListener")
        }
        task.addOnFailureListener { e ->
            Log.e("Phone", "startSMSRetrieverClient addOnFailureListener" + e.stackTrace)
        }
    }
}