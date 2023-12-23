package fit.asta.health.feature.walking.presentation.component


import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import fit.asta.health.designsystem.molecular.button.AppFilledButton
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.resources.strings.R
import fit.asta.health.resources.strings.R as StrR

/**
 * Welcome text shown when the app first starts, where the Healthcore APK is already installed.
 */
@Composable
fun InstalledMessage(onPermissionsLaunch: () -> Unit) {
    Column {
        BodyTexts.Level2(
            text = stringResource(id = StrR.string.installed_welcome_message),
            textAlign = TextAlign.Justify
        )
        AppFilledButton(
            onClick = { onPermissionsLaunch() }
        ) {
            BodyTexts.Level2(text = stringResource(R.string.permissions_button_label))
        }
    }
}

