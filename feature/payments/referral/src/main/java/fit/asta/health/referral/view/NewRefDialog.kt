package fit.asta.health.referral.view

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.button.AppIconButton
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.dialog.AppDialog
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.TitleTexts

@Preview(
    "Light Referral", heightDp = 1100
)
@Preview(
    name = "Dark Referral",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    heightDp = 1100
)
// Composable function for displaying a new referral dialog
@Composable
fun NewRefDialog() {
    AppTheme {
        // TODO: Add a proper onDismissRequest implementation
        AppDialog(onDismissRequest = { /*TODO*/ }) {
            // Content of the new referral dialog
            NewReferralDialogContent(
                shareRefLink = {},
                closeDialog = {},
                refCode = "0000",
            )
        }
    }
}

// Composable function for the content of the new referral dialog
@Composable
fun NewReferralDialogContent(
    refCode: String = "",
    shareRefLink: (String) -> Unit = {},
    closeDialog: () -> Unit = {},
) {
    AppCard {
        // Header section with a close button
        HeaderIconButton(closeDialog = closeDialog)

        // Main content of the dialog
        Column(
            Modifier
                .padding(horizontal = AppTheme.spacing.level2)
                .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //Referral Image
            ReferralImage()
            Spacer(modifier = Modifier.height(AppTheme.spacing.level2))
            TitleTexts.Level4(text = "Refer Friends & Earn")
            Spacer(modifier = Modifier.height(AppTheme.spacing.level1))
            BodyTexts.Level3(
                text = "Ask your friends to Signup with your referral code. Once done, both you and your friends each earn Cashback.",
                textAlign = TextAlign.Center,
                color = AppTheme.colors.onSurface.copy(alpha = 0.5f)
            )
            Spacer(modifier = Modifier.height(AppTheme.spacing.level2))
            // Composable function for displaying the referral code section
            ReferralCodeSection(refCode = refCode)
        }

        // Button for sharing the referral code
        ShareReferralButton(
            Modifier
                .fillMaxWidth()
                .height(AppTheme.spacing.level6), shareRefLink = { shareRefLink(refCode) }
        )
    }
}

// Composable function for displaying the referral code section
@Composable
private fun ReferralCodeSection(refCode: String = "") {
    // Display the title for the referral code section
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TitleTexts.Level2(
            text = "YOUR REFERRAL CODE",
            color = Color.LightGray
        )
    }

    // Display the referral code and provide an option to copy it
    CopyReferralCodeCard(
        colors = CardDefaults.cardColors(
            containerColor = AppTheme.colors.primary.copy(
                alpha = 0.2f
            )
        ), refCode = refCode
    )
}

// Composable function for displaying the close button in the header
@Composable
private fun HeaderIconButton(closeDialog: () -> Unit = {}) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
        AppIconButton(
            imageVector = Icons.Filled.Close,
            iconTint = Color.Black.copy(alpha = 0.5f), onClick = closeDialog
        )
    }
}