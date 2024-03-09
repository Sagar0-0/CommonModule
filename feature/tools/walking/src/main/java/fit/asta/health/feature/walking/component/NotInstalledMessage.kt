package fit.asta.health.feature.walking.component

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import fit.asta.health.common.utils.sendBugReportMessage
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.button.AppFilledButton
import fit.asta.health.designsystem.molecular.cards.AppCardWithColor
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.CaptionTexts
import fit.asta.health.resources.strings.R as StrR

/**
 * Welcome text shown when the Health Connect APK is not yet installed on the device, prompting the user
 * to install it.
 */
@Composable
fun NotInstalledMessage() {
    var showDetails by rememberSaveable {
        mutableStateOf(false)
    }
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
        Row(modifier = Modifier
            .clickable {
                showDetails = !showDetails
            }
            .padding(horizontal = AppTheme.spacing.level2)
            .padding(vertical = AppTheme.spacing.level1)
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level0),
            verticalAlignment = Alignment.CenterVertically) {
            CaptionTexts.Level3(
                modifier = Modifier.weight(1f),
                text = "Health Connect status"
            )
            AppIcon(
                imageVector = Icons.Default.KeyboardArrowRight
            )
        }
        AnimatedVisibility(visible = showDetails) {
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

    }
}

