package fit.asta.health.feature.auth.util

import android.app.Activity
import android.app.PendingIntent
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.auth.api.identity.GetPhoneNumberHintIntentRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.auth.api.phone.SmsRetrieverClient
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import fit.asta.health.feature.auth.screens.OTPReceiver
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

    /**
     * This Function starts an Intent to fetch the User's Phone Number Hints and auto fill the Phone
     * Number Text Field with the Fetched Phone Number
     *
     * @param onPhoneNumberChange This function is invoked when the Phone Number is changed
     * @param onCountryCodeChange This function is invoked when the Country Code is changed
     * @param onGenerateOtpClick This function is invoked when the generate code button is clicked
     */
    @Composable
    fun PhoneNumberHintIntentResultLauncher(
        onPhoneNumberChange: (String) -> Unit,
        onCountryCodeChange: (String) -> Unit,
        onGenerateOtpClick: () -> Unit
    ) {

        val context = LocalContext.current

        // This is the request Launcher Variable which launches an Intent and fetches the Phone Number hint
        val phoneNumberHintIntentResultLauncher =
            rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartIntentSenderForResult()
            ) { result ->
                try {

                    // Fetching the Phone Number Hints
                    val phoneNumberHint = Identity.getSignInClient(context as Activity)
                        .getPhoneNumberFromIntent(result.data)

                    // Changing the Country code and Phone Number with the Fetched values
                    onCountryCodeChange(phoneNumberHint.dropLast(10))
                    onPhoneNumberChange(phoneNumberHint.takeLast(10))

                    // Automatically Hitting the Generate OTP Button since the OTP is fetched directly
                    onGenerateOtpClick()
                } catch (e: Exception) {
                    Log.e("Phone Number Hint", e.toString())
                }
            }

        // Finally Requesting the Intent Launcher to launch this Intent
        val request: GetPhoneNumberHintIntentRequest =
            GetPhoneNumberHintIntentRequest.builder().build()

        try {

            // Starting the Intent
            Identity.getSignInClient(context as Activity)
                .getPhoneNumberHintIntent(request)
                .addOnSuccessListener { result: PendingIntent ->
                    try {
                        phoneNumberHintIntentResultLauncher.launch(
                            IntentSenderRequest
                                .Builder(result)
                                .build()
                        )
                    } catch (e: Exception) {
                        Log.e("Phone Number Hint", e.toString())
                    }
                }
                .addOnFailureListener {
                    Log.e("Phone Number Hint", it.toString())
                }
        } catch (e: Exception) {
            Log.e("Phone Number Hint", e.toString())
        }
    }

    /**
     * This function starts the BroadCast Receiver to fetch the User's SMS Messages and auto fill the
     * OTP Text Field with the Fetched SMS
     *
     * @param onOtpTextChange This function is triggered when the OTP text is changed
     * @param onVerifyOtpClick This function is triggered when the OTP is fetched from the Receiver
     */
    @Composable
    fun SmsReceiver(
        onOtpTextChange: (String) -> Unit,
        onVerifyOtpClick: () -> Unit
    ) {

        val context = LocalContext.current

        // This is the request Launcher which launches an Intent to fetch the user's SMS Messages
        val smsReceiverLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartActivityForResult(),
            onResult = { result ->
                if (result.resultCode == Activity.RESULT_OK && result.data != null) {

                    // Fetching SMS message content
                    val message = result.data!!.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE)

                    // Finding the OTP Code from the whole SMS content and updating the OTP
                    val smsCode = message?.let { "[0-9]{6}".toRegex().find(it) }
                    smsCode?.value?.let {
                        onOtpTextChange(it)
                    }

                    // Starting the Flow to verify the OTP
                    onVerifyOtpClick()
                } else
                    Toast.makeText(context, "Failed to retrieve OTP", Toast.LENGTH_SHORT).show()
            }
        )

        // Starting the Broadcast Receiver to fetch the SMS messages of the User
        DisposableEffect(context) {
            val myOTPReceiver = OTPReceiver(
                onSuccess = { intent ->
                    smsReceiverLauncher.launch(intent)
                },
                onFailure = {
                    Toast.makeText(context, "Failed to retrieve OTP", Toast.LENGTH_SHORT).show()
                }
            )

            // Registering the Receiver
            myOTPReceiver.register(context)

            // Upon Dispose we unregister the Receiver
            onDispose { myOTPReceiver.unregister(context) }
        }
    }
}