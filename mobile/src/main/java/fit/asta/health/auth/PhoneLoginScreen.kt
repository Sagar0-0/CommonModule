package fit.asta.health.auth

import android.app.Activity
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.gms.auth.api.identity.GetPhoneNumberHintIntentRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import fit.asta.health.common.ui.components.ValidatedNumberField
import fit.asta.health.common.ui.theme.spacing
import java.util.concurrent.TimeUnit

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun PhoneUIPreview() {
    PhoneLoginScreen {

    }
}

@Composable
fun PhoneLoginScreen(onSuccess: () -> Unit) {

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

    val focusRequester = remember { FocusRequester() }

    val mAuth: FirebaseAuth = Firebase.auth
    lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

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
            val credential: PhoneAuthCredential =
                PhoneAuthProvider.getCredential(
                    verificationID, otp
                )
            mAuth.signInWithCredential(credential)
                .addOnCompleteListener(context as Activity) { task ->
                    if (task.isSuccessful) {
                        onSuccess()
                    } else {
                        loading = false
                        if (task.exception is FirebaseAuthInvalidCredentialsException) {
                            Toast.makeText(
                                context,
                                "Verification failed.." + (task.exception as FirebaseAuthInvalidCredentialsException).message,
                                Toast.LENGTH_LONG
                            ).show()
                            if ((task.exception as FirebaseAuthInvalidCredentialsException).message?.contains(
                                    "expired"
                                ) == true
                            ) {
                                codeSent = false
                            }
                        }
                    }
                }
        }
    }

    val smsReceiverLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { result ->
            if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                // Get SMS message content
                val message = result.data!!.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE)
                val oneTimeCode = parseOneTimeCode(message)
                otp = oneTimeCode
                onOtpSubmit()
            } else {
                Toast.makeText(context, "Otp retrieval failed", Toast.LENGTH_SHORT).show()
            }
        }
    )

    val smsVerificationReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (SmsRetriever.SMS_RETRIEVED_ACTION == intent.action) {
                val extras = intent.extras
                val smsRetrieverStatus = extras?.get(SmsRetriever.EXTRA_STATUS) as? Status
                if (smsRetrieverStatus == null) {
                    Log.e("SMS", "onReceive: smsRetrieverStatus null")
                } else {
                    when (smsRetrieverStatus.statusCode) {
                        CommonStatusCodes.SUCCESS -> {
                            // Get consent intent
                            try {
                                val consentIntent =
                                    extras.getParcelable<Intent>(SmsRetriever.EXTRA_CONSENT_INTENT)
                                smsReceiverLauncher.launch(consentIntent)
                            } catch (e: java.lang.Exception) {
                                Log.e("SMS", "onReceive: ${e.message}")
                            }
                        }

                        CommonStatusCodes.TIMEOUT -> {
                            Toast.makeText(context, "Timeout error", Toast.LENGTH_SHORT).show()
                        }

                        else -> {
                            Toast.makeText(
                                context,
                                "Something went wrong, try again!",
                                Toast.LENGTH_SHORT
                            ).show()
                            codeSent = false
                        }
                    }
                }

            }
        }
    }

    val onSendOtp = {
        if (TextUtils.isEmpty(phoneNumber) || phoneNumber.length < 10 || TextUtils.isEmpty(
                postalCode
            ) || postalCode.length < 3
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
            SmsRetriever.getClient(context).startSmsUserConsent(null)
            val intentFilter = IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)
            context.registerReceiver(smsVerificationReceiver, intentFilter)
            sendVerificationCode(number, mAuth, context as Activity, callbacks)
        }
    }

    val phoneNumberHintIntentResultLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartIntentSenderForResult()) { result ->
            try {
                val phoneNumberHint = Identity.getSignInClient(context as Activity)
                    .getPhoneNumberFromIntent(result.data)
                postalCode = phoneNumberHint.substring(0, 3)
                phoneNumber = phoneNumberHint.substring(3)
                focusRequester.freeFocus()
                onSendOtp()
            } catch (e: Exception) {
                Log.e(TAG, "Phone Number Hint failed")
            }
        }

    val getPhoneNumberHint = {
        val request: GetPhoneNumberHintIntentRequest =
            GetPhoneNumberHintIntentRequest.builder().build()
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
    }


    Column(
        modifier = Modifier
            .fillMaxSize(),
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
                onValueChange = { if (it.length <= 3) postalCode = it },
                placeholder = "+91",
                modifier = Modifier
                    .focusRequester(focusRequester)
                    .onFocusChanged {
                        if (it.isFocused) {
                            getPhoneNumberHint()
                        }
                    }
                    .padding(
                        top = spacing.medium,
                        bottom = spacing.medium,
                        start = spacing.medium,
                        end = spacing.small
                    )
                    .weight(0.25f),
                singleLine = true,
                supportingText = "${postalCode.length} / 3",
                supportingTextModifier = Modifier.fillMaxWidth(),
                supportingTextAlign = TextAlign.End
            )

            ValidatedNumberField(
                enabled = !codeSent && !loading,
                value = phoneNumber,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                onValueChange = { if (it.length <= 10) phoneNumber = it },
                placeholder = "Enter your phone number",
                modifier = Modifier
                    .focusRequester(focusRequester)
                    .onFocusChanged {
                        if (it.isFocused) {
                            getPhoneNumberHint()
                        }
                    }
                    .padding(top = spacing.medium, bottom = spacing.medium, end = spacing.medium)
                    .weight(0.75f),
                singleLine = true,
                supportingText = "${phoneNumber.length} / 10",
                supportingTextModifier = Modifier.fillMaxWidth(),
                supportingTextAlign = TextAlign.End
            )
        }

        Spacer(modifier = Modifier.height(spacing.medium))

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
                    .padding(spacing.medium)
            ) {
                Text(text = "Generate OTP", modifier = Modifier.padding(spacing.small))
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

                Spacer(modifier = Modifier.height(spacing.medium))

                Button(
                    enabled = !loading,
                    onClick = {
                        onOtpSubmit()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(spacing.medium)
                ) {
                    Text(text = "Verify OTP", modifier = Modifier.padding(spacing.small))
                }
            }
        }
    }

    if (loading) {
        LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
    }

    callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(p0: PhoneAuthCredential) {
            Toast.makeText(context, "Verification successful..", Toast.LENGTH_SHORT).show()
        }

        override fun onVerificationFailed(p0: FirebaseException) {
            Toast.makeText(context, "Verification failed.. ${p0.message}", Toast.LENGTH_LONG)
                .show()
            loading = false
            codeSent = false
        }

        override fun onCodeSent(
            verificationId: String,
            p1: PhoneAuthProvider.ForceResendingToken
        ) {
            super.onCodeSent(verificationId, p1)
            verificationID = verificationId
            codeSent = true
            loading = false
        }
    }

}

fun parseOneTimeCode(message: String?): String {
    return message?.substring(0, 6) ?: ""
}

private fun sendVerificationCode(
    number: String,
    auth: FirebaseAuth,
    activity: Activity,
    callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
) {
    val options = PhoneAuthOptions.newBuilder(auth)
        .setPhoneNumber(number)
        .setTimeout(120L, TimeUnit.SECONDS)
        .setActivity(activity)
        .setCallbacks(callbacks)
        .build()
    PhoneAuthProvider.verifyPhoneNumber(options)
}