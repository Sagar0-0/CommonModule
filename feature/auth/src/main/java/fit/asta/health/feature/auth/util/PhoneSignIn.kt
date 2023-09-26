package fit.asta.health.feature.auth.util

import android.app.Activity
import android.app.PendingIntent
import android.content.ContentValues
import android.content.Context
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.android.gms.auth.api.identity.GetPhoneNumberHintIntentRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.auth.api.phone.SmsRetrieverClient
import com.google.firebase.FirebaseException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import fit.asta.health.designsystem.components.ValidatedNumberField
import fit.asta.health.designsystem.components.generic.LoadingAnimation
import fit.asta.health.designsystemx.AstaThemeX
import fit.asta.health.feature.auth.screens.OTPReceiver
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit
import kotlin.time.Duration.Companion.seconds

@Composable
fun PhoneSignIn(
    failed: Boolean = false,
    resetFailedState: () -> Unit,
    signInWithCredentials: (AuthCredential) -> Unit
) {
    var phoneNumber by rememberSaveable {
        mutableStateOf("")
    }

    var postalCode by rememberSaveable {
        mutableStateOf("+91")
    }

    var otp by rememberSaveable {
        mutableStateOf("")
    }

    var verificationID by rememberSaveable {
        mutableStateOf("")
    }

    var codeSent by rememberSaveable {
        mutableStateOf(false)
    }

    var loading by rememberSaveable {
        mutableStateOf(false)
    }

    val context = LocalContext.current

    LaunchedEffect(failed) {
        if (failed) {
            loading = false
            codeSent = false
            otp = ""
            resetFailedState()
            Toast.makeText(
                context,
                "Please check if this number already linked with another account.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    var ticks by rememberSaveable { mutableIntStateOf(10) }
    LaunchedEffect(codeSent) {
        if (codeSent) {
            while (ticks > 0) {
                delay(1.seconds)
                ticks--
            }
        } else {
            ticks = 10
        }
    }


    val mAuth: FirebaseAuth = Firebase.auth
    lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    var shouldStartSMSRetrieval by rememberSaveable {
        mutableStateOf(false)
    }

    LaunchedEffect(shouldStartSMSRetrieval) {
        if (shouldStartSMSRetrieval) {
            Log.d(ContentValues.TAG, "SMS Retrieval is Starting...")
            startSMSRetrieverClient(context)
        }
    }

    val onOtpSubmit = {
        if (TextUtils.isEmpty(otp) || otp.length < 6) {
            Toast.makeText(
                context,
                "Please enter a valid OTP",
                Toast.LENGTH_SHORT
            )
                .show()
        } else {
            loading = true
            shouldStartSMSRetrieval = false
            val credential: AuthCredential = PhoneAuthProvider.getCredential(verificationID, otp)
            signInWithCredentials(credential)
        }
    }

    val smsReceiverLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { result ->
            if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                // Get SMS message content
                val message = result.data!!.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE)
                val smsCode = message?.let { "[0-9]{6}".toRegex().find(it) }
                smsCode?.value?.let {
                    otp = it
                }
                Log.d("OTP", "PhoneLoginScreen: $message")
                onOtpSubmit()
            } else {
                Toast.makeText(context, "Otp retrieval failed", Toast.LENGTH_SHORT).show()
            }
        }
    )

    DisposableEffect(context) {
        Log.d("OTP", "PhoneLoginScreen: Registered Receiver")
        val myOTPReceiver = OTPReceiver(
            onSuccess = { intent ->
                Log.d(ContentValues.TAG, "OTP Received $intent")
                smsReceiverLauncher.launch(intent)
            },
            onFailure = {
                Log.e(ContentValues.TAG, "Timeout")
                Toast.makeText(context, "Otp retrieval failed", Toast.LENGTH_SHORT).show()
                loading = false
                codeSent = false
            }
        )
        myOTPReceiver.register(context)
        onDispose {
            myOTPReceiver.unregister(context)
        }
    }

    val onSendOtp = {
        if (TextUtils.isEmpty(phoneNumber) || phoneNumber.length < 10 || TextUtils.isEmpty(
                postalCode
            ) || postalCode.length < 2
        ) {
            Toast.makeText(
                context,
                "Enter a valid phone number",
                Toast.LENGTH_SHORT
            )
                .show()
        } else {
            loading = true
            val number = "${postalCode}${phoneNumber}"
            startSMSRetrieverClient(context)
            startPhoneVerification(number, mAuth, context as Activity, callbacks)
        }
    }

    val phoneNumberHintIntentResultLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartIntentSenderForResult()
        ) { result ->
            try {
                val phoneNumberHint = Identity.getSignInClient(context as Activity)
                    .getPhoneNumberFromIntent(result.data)
                postalCode = phoneNumberHint.dropLast(10)
                phoneNumber = phoneNumberHint.takeLast(10)
                onSendOtp()
            } catch (e: Exception) {
                Log.e("Phone", "Phone Number Hint failed $e")
            }
        }

    val getPhoneNumberHint = {
        val request: GetPhoneNumberHintIntentRequest =
            GetPhoneNumberHintIntentRequest.builder().build()
        try {
            Identity.getSignInClient(context as Activity)
                .getPhoneNumberHintIntent(request)
                .addOnSuccessListener { result: PendingIntent ->
                    try {
                        phoneNumberHintIntentResultLauncher.launch(
                            IntentSenderRequest.Builder(result).build()
                        )
                    } catch (e: Exception) {
                        Log.e("Phone", "Launching the PendingIntent failed")
                    }
                }
                .addOnFailureListener {
                    Log.e("Phone", "Phone Number Hint failed")
                }
        } catch (e: Exception) {
            Log.e(ContentValues.TAG, "PhoneLoginScreen: Phone hint exception")
        }
    }


    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            ValidatedNumberField(
                enabled = !codeSent && !loading,
                value = postalCode,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                onValueChange = { if (it.length in 1..4) postalCode = it },
                modifier = Modifier
                    .padding(
                        top = AstaThemeX.appSpacing.medium,
                        bottom = AstaThemeX.appSpacing.medium,
                        start = AstaThemeX.appSpacing.medium,
                        end = AstaThemeX.appSpacing.small
                    )
                    .weight(0.3f)
                    .onFocusChanged {
                        if (it.isFocused) {
                            getPhoneNumberHint()
                        }
                    },
                singleLine = true
            )

            ValidatedNumberField(
                enabled = !codeSent && !loading,
                value = phoneNumber,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                onValueChange = { if (it.length <= 10) phoneNumber = it },
                placeholder = "Enter your phone number",
                modifier = Modifier
                    .padding(
                        top = AstaThemeX.appSpacing.medium,
                        bottom = AstaThemeX.appSpacing.medium,
                        end = AstaThemeX.appSpacing.medium
                    )
                    .weight(0.7f)
                    .onFocusChanged {
                        if (it.isFocused) {
                            getPhoneNumberHint()
                        }
                    },
                singleLine = true,
                supportingText = "${phoneNumber.length} / 10",
                supportingTextModifier = Modifier.fillMaxWidth(),
                supportingTextAlign = TextAlign.End
            )
        }

        Spacer(modifier = Modifier.height(AstaThemeX.appSpacing.medium))

        AnimatedVisibility(
            visible = !codeSent,
            exit = scaleOut(
                targetScale = 0.5f,
                animationSpec = tween(durationMillis = 500, delayMillis = 100)
            ),
            enter = scaleIn(
                initialScale = 0.5f,
                animationSpec = tween(durationMillis = 500, delayMillis = 100)
            )
        ) {
            Button(
                enabled = !loading && !codeSent,
                onClick = {
                    onSendOtp()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(AstaThemeX.appSpacing.medium)
            ) {
                Text(text = "Generate OTP", modifier = Modifier.padding(AstaThemeX.appSpacing.small))
            }
        }

        AnimatedVisibility(
            visible = codeSent,
            enter = scaleIn(
                initialScale = 0.5f,
                animationSpec = tween(durationMillis = 500, delayMillis = 100)
            ),
            exit = scaleOut(
                targetScale = 0.5f,
                animationSpec = tween(durationMillis = 500, delayMillis = 100)
            )
        ) {
            Column {
                ValidatedNumberField(
                    enabled = !loading,
                    value = otp,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    onValueChange = { if (it.length <= 6) otp = it },
                    placeholder = "Enter your otp",
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    singleLine = true,
                    supportingText = "${otp.length} / 6",
                    supportingTextModifier = Modifier.fillMaxWidth(),
                    supportingTextAlign = TextAlign.End
                )

                Spacer(modifier = Modifier.height(AstaThemeX.appSpacing.medium))

                Button(
                    enabled = !loading,
                    onClick = {
                        onOtpSubmit()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(AstaThemeX.appSpacing.medium)
                ) {
                    Text(
                        text = "Verify OTP",
                        modifier = Modifier.padding(AstaThemeX.appSpacing.small)
                    )
                }
                TextButton(
                    enabled = !loading && ticks == 0,
                    onClick = { codeSent = false },
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(AstaThemeX.appSpacing.medium)
                ) {
                    if (ticks > 0) {
                        Text(text = "Resend code in $ticks seconds")
                    } else {
                        Text(
                            text = "Still not received?",
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            }
        }
    }

    if (loading) {
        LoadingAnimation()
    }

    callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            Log.d("OTP", "onVerificationCompleted:$credential")
            signInWithCredentials(credential)
        }

        override fun onVerificationFailed(p0: FirebaseException) {
            Toast.makeText(context, "Verification failed.. ${p0.message}", Toast.LENGTH_LONG)
                .show()
            loading = false
            codeSent = false
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            super.onCodeSent(verificationId, token)
            verificationID = verificationId
            codeSent = true
            loading = false
        }
    }

}


private fun startPhoneVerification(
    number: String,
    auth: FirebaseAuth,
    activity: Activity,
    callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
) {
    val options = PhoneAuthOptions.newBuilder(auth)
        .setPhoneNumber(number)
        .setTimeout(0L, TimeUnit.SECONDS)
        .setActivity(activity)
        .setCallbacks(callbacks)
        .build()
    PhoneAuthProvider.verifyPhoneNumber(options)
}

private fun startSMSRetrieverClient(context: Context) {
    val client: SmsRetrieverClient = SmsRetriever.getClient(context)
    val task = client.startSmsUserConsent(null)
    task.addOnSuccessListener {
        Log.d("Phone", "startSMSRetrieverClient addOnSuccessListener")
    }
    task.addOnFailureListener { e ->
        Log.e("Phone", "startSMSRetrieverClient addOnFailureListener" + e.stackTrace)
    }
}