package fit.asta.health.feature.walking.view.home

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.ButtonWithColor
import fit.asta.health.designsystem.molecular.DialogContent
import fit.asta.health.designsystem.molecular.background.appRememberBottomSheetScaffoldState
import fit.asta.health.designsystem.molecular.button.AppElevatedButton
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.dialog.AppAlertDialog
import fit.asta.health.designsystem.molecular.dialog.AppDialog
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.feature.walking.vm.WalkingViewModel

@Composable
fun PlayStorePermsUI() {
   // val count by walkingViewModel.stepsPermissionRejectedCount.collectAsStateWithLifecycle()
    var isDialogVisible by rememberSaveable { mutableStateOf(true) }
    var showDetails by rememberSaveable {
        mutableStateOf(false)
    }
//    if(count != 0){
//        isDialogVisible = false
//    }
    val context = LocalContext.current
    AnimatedVisibility(isDialogVisible) {
        AppAlertDialog(onDismissRequest = {
            isDialogVisible = false
        },
            text = {
                Column {
                    BodyTexts.Level2(text = "Your device doesn't have inbuilt support for HealthConnect, You can download from here!")
                    BodyTexts.Level1(text = "Note :", modifier = Modifier.clickable{
                        showDetails = !showDetails
                    })
                    AnimatedVisibility(visible = showDetails) {
                        BodyTexts.Level2(text = "you can also install it later at any point of time . However,It is recommended to install, in order to track your health effectively", modifier = Modifier.padding(10.dp))
                    }
                }
                
            },
            title = {
                TitleTexts.Level2(text = "Download from Store")
            },
            icon = {
//                AppIcon(painter = painterResource(id = R.))
            },
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false
            ),
            confirmButton = {
                ButtonWithColor(color = AppTheme.colors.primary, text = "Install") {
                    downloadIntent(context)
                }
                  },
            dismissButton = {
                ButtonWithColor(color = AppTheme.colors.onPrimaryContainer, text = "Decline") {
                    isDialogVisible = false
                }
            }
        )
    }
}


private fun downloadIntent(context : Context){
    val packageName = "com.google.android.apps.healthdata" // Package name for the HealthConnect app
    try {
        // Open the app page in the Play Store
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName"))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    } catch (e: android.content.ActivityNotFoundException) {
        // If Play Store is not available, open the app page in the browser
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$packageName"))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }
}