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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
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
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.animations.AppCircularProgressIndicator
import fit.asta.health.designsystem.molecular.button.AppTextButton
import fit.asta.health.designsystem.molecular.textfield.AppTextField
import fit.asta.health.designsystem.molecular.textfield.AppTextFieldType
import fit.asta.health.designsystem.molecular.textfield.AppTextFieldValidator
import fit.asta.health.feature.auth.screens.OTPReceiver
import fit.asta.otpfield.OTPInput
import fit.asta.otpfield.configuration.OTPCellConfiguration
import fit.asta.otpfield.configuration.OTPConfigurations
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit
import kotlin.time.Duration.Companion.seconds
import fit.asta.health.resources.strings.R as StringR

@Composable
fun PhoneSignIn(
    failed: Boolean = false,
    resetFailedState: () -> Unit,
    isPhoneEntered: (Boolean) -> Unit = {},
    signInWithCredentials: (AuthCredential) -> Unit
) {
    var phoneNumber by rememberSaveable {
        mutableStateOf("")
    }

    var postalCode by rememberSaveable {
        mutableStateOf("+91")
    }

    var otpValue by rememberSaveable {
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
            isPhoneEntered(codeSent)
            otpValue = ""
            resetFailedState()
            Toast.makeText(
                context,
                "Please check if this number already linked with another account.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    var ticks by rememberSaveable { mutableIntStateOf(30) }
    LaunchedEffect(codeSent) {
        if (codeSent) {
            while (ticks > 0) {
                delay(1.seconds)
                ticks--
            }
        } else {
            ticks = 30
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
        if (TextUtils.isEmpty(otpValue) || otpValue.length < 6) {
            Toast.makeText(
                context,
                "Please enter a valid OTP",
                Toast.LENGTH_SHORT
            )
                .show()
        } else {
            loading = true
            shouldStartSMSRetrieval = false
            val credential: AuthCredential =
                PhoneAuthProvider.getCredential(verificationID, otpValue)
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
                    otpValue = it
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
                isPhoneEntered(codeSent)
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
            AppTextField(
                appTextFieldType = AppTextFieldValidator(AppTextFieldType.Custom(2, 4)),
                enabled = !codeSent && !loading,
                value = postalCode,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                onValueChange = { if (it.length in 1..4) postalCode = it },
                modifier = Modifier
                    .padding(
                        top = AppTheme.spacing.level2,
                        bottom = AppTheme.spacing.level2,
                        start = AppTheme.spacing.level2,
                        end = AppTheme.spacing.level1
                    )
                    .weight(0.3f)
                    .onFocusChanged {
                        if (it.isFocused) {
                            getPhoneNumberHint()
                        }
                    },
                singleLine = true
            )

            AppTextField(
                appTextFieldType = AppTextFieldValidator(AppTextFieldType.Phone),
                enabled = !codeSent && !loading,
                value = phoneNumber,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                onValueChange = { if (it.length <= 10) phoneNumber = it },
                modifier = Modifier
                    .padding(
                        top = AppTheme.spacing.level2,
                        bottom = AppTheme.spacing.level2,
                        end = AppTheme.spacing.level2
                    )
                    .weight(0.7f)
                    .onFocusChanged {
                        if (it.isFocused) {
                            getPhoneNumberHint()
                        }
                    },
                singleLine = true
            )
        }

        Spacer(modifier = Modifier.height(AppTheme.spacing.level2))

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
            AppTextButton(
                textToShow = stringResource(id = StringR.string.generate_otp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(AppTheme.spacing.level2),
                enabled = !loading && !codeSent
            ) {
                onSendOtp()
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
                val defaultConfig = OTPCellConfiguration.withDefaults()
                OTPInput(
                    modifier = Modifier
                        .padding(AppTheme.spacing.level1)
                        .fillMaxWidth(),
                    enabled = !loading,
                    value = otpValue,
                    onValueChange = { newValue, isValid ->
                        otpValue = newValue
                        if (isValid) {
                            // Validate the value here...
                        }
                    },
                    /* when the value is 1111, all cells will use errorCellConfig */
                    isValueInvalid = otpValue == "111111",
                    configurations = OTPConfigurations.withDefaults(
                        cellsCount = 6,
                        emptyCellConfig = defaultConfig,
                        filledCellConfig = defaultConfig,
                        activeCellConfig = defaultConfig.copy(
                            borderColor = AppTheme.colors.onSurface,
                            borderWidth = AppTheme.elevation.level1
                        ),
                        errorCellConfig = defaultConfig.copy(
                            borderColor = AppTheme.colors.error,
                            borderWidth = AppTheme.elevation.level1
                        ),
                        placeHolder = "*",
                        cellModifier = Modifier
                            .padding(horizontal = AppTheme.spacing.level0)
                            .size(AppTheme.customSize.level6),
                    ),
                )

                Spacer(modifier = Modifier.height(AppTheme.spacing.level2))

                AppTextButton(
                    textToShow = stringResource(id = StringR.string.verify_otp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(AppTheme.spacing.level2),
                    enabled = !loading
                ) {
                    onOtpSubmit()
                }
                AppTextButton(
                    textToShow = if (ticks > 0) "Resend code in $ticks seconds" else "Still not received?",
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(AppTheme.spacing.level2),
                    enabled = !loading && ticks == 0
                ) {
                    codeSent = false
                    isPhoneEntered(codeSent)
                }
            }
        }
    }

    if (loading) {
        AppCircularProgressIndicator()
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
            isPhoneEntered(codeSent)
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            super.onCodeSent(verificationId, token)
            verificationID = verificationId
            codeSent = true
            isPhoneEntered(codeSent)
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