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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
@Composable
fun NewRefDialog() {
    AppTheme {
        AppDialog(onDismissRequest = { /*TODO*/ }) {
            NewRefDialogContent(
                shareRefLink = {},
                closeDialog = {},
                copyRefCode = {},
            )
        }
    }
}


@Composable
private fun NewRefDialogContent(
    shareRefLink: () -> Unit = {},
    closeDialog: () -> Unit = {},
    copyRefCode: () -> Unit = {},
) {
    AppCard {
        Column(
            Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        ) {
            HeaderIconBtn(closeDialog = closeDialog)
            ReferralImg()
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TitleTexts.Level4(text = "Refer Friends & Earn")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BodyTexts.Level3(
                    text = "Ask your friends to Signup with your referral code. Once done, both you and your friends each earn Cashback.",
                    textAlign = TextAlign.Center,
                    color = AppTheme.colors.onSurface.copy(alpha = 0.5f)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            ReferralCodeSection(copyRefCode = copyRefCode)
        }
        ShareRefBtn(
            Modifier
                .fillMaxWidth()
                .height(AppTheme.spacing.level6), shareRefLink = shareRefLink
        )
    }
}

@Composable
private fun ReferralCodeSection(copyRefCode: () -> Unit = {}) {
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
    /*CopyRefCodeCard(
        colors = CardDefaults.cardColors(
            containerColor = AppTheme.colors.primary.copy(
                alpha = 0.2f
            )
        ), copyRefCode = copyRefCode
    )*/
}

@Composable
private fun HeaderIconBtn(closeDialog: () -> Unit = {}) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
        AppIconButton(
            imageVector = Icons.Filled.Close,
            iconTint = Color.Black.copy(alpha = 0.5f), onClick = closeDialog
        )
    }
}