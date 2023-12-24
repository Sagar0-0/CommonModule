package fit.asta.health.feature.walking.component

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import fit.asta.health.designsystem.molecular.button.AppFilledButton
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.resources.strings.R as StrR

/**
 * Welcome text shown when the Health Connect APK is not yet installed on the device, prompting the user
 * to install it.
 */
@Composable
fun NotInstalledMessage() {
    // Build the URL to allow the user to install the Health Connect package
    val url = Uri.parse(stringResource(id = StrR.string.market_url))
        .buildUpon()
        .appendQueryParameter("id", stringResource(id = StrR.string.health_connect_package))
        // Additional parameter to execute the onboarding flow.
        .appendQueryParameter("url", stringResource(id = StrR.string.onboarding_url))
        .build()
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        BodyTexts.Level2(
            text = stringResource(id = StrR.string.not_installed_description),
            textAlign = TextAlign.Justify
        )
        AppFilledButton(
            onClick = {
                context.startActivity(
                    Intent(Intent.ACTION_VIEW, url)
                )
            }
        ) {
            BodyTexts.Level2(text = stringResource(StrR.string.not_installed_link_text))
        }
    }
}

