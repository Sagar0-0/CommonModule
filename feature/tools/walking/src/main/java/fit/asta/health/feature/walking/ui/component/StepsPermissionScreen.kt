package fit.asta.health.feature.walking.ui.component

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import fit.asta.health.data.walking.service.StepService
import fit.asta.health.designsystem.molecular.background.AppScaffold
import fit.asta.health.designsystem.molecular.button.AppFilledButton
import fit.asta.health.designsystem.molecular.texts.TitleTexts


@Composable
fun StepsPermissionScreen(
    stepsPermissionCount: Int,
    goToSteps: () -> Unit,
    setPermissionCount: (Int) -> Unit
) {

    val context = LocalContext.current as Activity
    LaunchedEffect(key1 = Unit, block = {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACTIVITY_RECOGNITION
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            goToSteps()
        }

    })
    val stepsPermissionResultLauncher: ActivityResultLauncher<String> =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission()
        ) { perms ->
            if (perms) {
                Toast.makeText(
                    context,
                    "This permission is recommended for better functionality.",
                    Toast.LENGTH_SHORT
                ).show()
                val launchIntent = Intent(context, StepService::class.java)
                ContextCompat.startForegroundService(context, launchIntent)
                setPermissionCount(0)
                goToSteps()
            } else {
                Toast.makeText(
                    context,
                    "Please allow Steps permission access.",
                    Toast.LENGTH_SHORT
                ).show()
                setPermissionCount(stepsPermissionCount + 1)
            }
        }

    val checkPermissionAndLaunchScheduler = {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACTIVITY_RECOGNITION
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            Log.d("rishi","Activity Recognition perms granted")
            val launchIntent = Intent(context, StepService::class.java)
            ContextCompat.startForegroundService(context, launchIntent)
            setPermissionCount(0)
            goToSteps()
        } else {
            if (stepsPermissionCount > 2) {
                Toast.makeText(
                    context,
                    "Please allow Steps permission access.",
                    Toast.LENGTH_SHORT
                )
                    .show()
                with(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)) {
                    data = Uri.fromParts("package", context.packageName, null)
                    addCategory(Intent.CATEGORY_DEFAULT)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                    addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
                    context.startActivity(this)
                }
            } else {
                Log.d("rishi","Activity Recognition perms not granted so here")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    stepsPermissionResultLauncher.launch(Manifest.permission.ACTIVITY_RECOGNITION)
                } else {
                    Log.d("rishi","permission Count : $stepsPermissionCount")
                    val launchIntent = Intent(context, StepService::class.java)
                    ContextCompat.startForegroundService(context, launchIntent)
                    goToSteps()
                }
            }
        }
    }

    AppScaffold(modifier = Modifier.fillMaxSize()) {

        PermissionScreen(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            text = "Please let me see the total steps your phone detects",
            buttonText = "Access physical activity"
        ) {
            // open steps permission
            checkPermissionAndLaunchScheduler()
        }
    }
}

@Composable
fun PermissionScreen(modifier: Modifier, text: String, buttonText: String, onClick: () -> Unit) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Column(
            modifier = Modifier.weight(.5f),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TitleTexts.Level2(text = text, maxLines = 3)
        }
        Column(
            modifier = Modifier.weight(.5f),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AppFilledButton(textToShow = buttonText) {
                onClick()
            }
        }
    }
}