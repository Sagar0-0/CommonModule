package fit.asta.health.referral.view

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Diamond
import androidx.compose.material.icons.filled.GroupAdd
import androidx.compose.material.icons.filled.Link
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import fit.asta.health.common.utils.copyTextToClipboard
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.background.AppSurface
import fit.asta.health.designsystem.molecular.button.AppFilledButton
import fit.asta.health.designsystem.molecular.button.AppIconButton
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.image.AppLocalImage
import fit.asta.health.designsystem.molecular.image.AppNetworkImage
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.CaptionTexts
import fit.asta.health.designsystem.molecular.texts.HeadingTexts
import fit.asta.health.designsystem.molecular.texts.LargeTexts
import fit.asta.health.referral.remote.model.UserDetails
import fit.asta.health.resources.drawables.R


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
fun NewReferralDesign(
    modifier: Modifier = Modifier,
    shareRefLink: (String) -> Unit = {},
    refCode: String = "",
    referredUserList: List<UserDetails>? = null,
) {
    AppTheme {
        AppSurface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(AppTheme.spacing.level2))
                ReferralImage()
                Spacer(modifier = Modifier.height(AppTheme.spacing.level2))
                ShareReferralButton(shareRefLink = { shareRefLink(refCode) })
                LargeTexts.Level2(
                    text = "OR",
                    color = AppTheme.colors.onSurfaceVariant,
                )
                CopyReferralCodeCard(refCode = refCode)
                Spacer(modifier = Modifier.height(AppTheme.spacing.level2))
                InvitationReport()
                Spacer(modifier = Modifier.height(AppTheme.spacing.level2))
                referredUserList?.let {
                    HeadingTexts.Level2(text = "You've invited...")
                    Spacer(modifier = Modifier.height(AppTheme.spacing.level3))
                    referredUserList.forEach { user ->
                        InvitedUserList(user)
                    }
                }
            }
        }
    }
}


@Composable
fun ReferralImage(
    refImg: Int = R.drawable.ref_ed_2,
    aspectRatio: Float = AppTheme.aspectRatio.wideScreen
) {
    AppLocalImage(
        painter = painterResource(id = refImg),
        modifier = Modifier.aspectRatio(aspectRatio),
        contentScale = ContentScale.Fit
    )
}


@Composable
fun ShareReferralButton(modifier: Modifier = Modifier, shareRefLink: () -> Unit = {}) {
    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
    ) {
        AppFilledButton(
            textToShow = "Share your link",
            trailingIcon = Icons.Filled.Link, onClick = shareRefLink, modifier = modifier
        )
    }
}


@Composable
fun CopyReferralCodeCard(
    refCode: String = "",
    colors: CardColors = CardDefaults.cardColors(),
) {

    val context = LocalContext.current

    Row(
        Modifier
            .fillMaxWidth()
            .padding(vertical = AppTheme.spacing.level2),
        horizontalArrangement = Arrangement.Center
    ) {
        AppCard(colors = colors, onClick = { context.copyTextToClipboard(refCode) }) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(
                    PaddingValues(
                        start = AppTheme.spacing.level3,
                        top = AppTheme.spacing.level1,
                        end = AppTheme.spacing.level3,
                        bottom = AppTheme.spacing.level1
                    )
                )
            ) {
                HeadingTexts.Level1(
                    text = refCode,
                    modifier = Modifier.padding(AppTheme.spacing.level2),
                    textAlign = TextAlign.Center,
                    color = AppTheme.colors.primary
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    AppIcon(imageVector = Icons.Filled.ContentCopy, tint = AppTheme.colors.primary)
                    Spacer(modifier = Modifier.width(AppTheme.spacing.level0))
                    CaptionTexts.Level1(text = "Copy", color = AppTheme.colors.primary)
                }
            }
        }
    }
}


@Composable
fun InvitedUserList(
    userDetails: UserDetails,
    addToCommunity: () -> Unit = {}
) {
    AppCard(modifier = Modifier.padding(horizontal = AppTheme.spacing.level2)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = AppTheme.spacing.level2, horizontal = AppTheme.spacing.level2),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row {
                AppNetworkImage(
                    model = userDetails.pic,
                    contentDescription = "Profile",
                    modifier = Modifier
                        .size(AppTheme.boxSize.level6)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(AppTheme.spacing.level1))
                Column {
                    HeadingTexts.Level3(text = userDetails.name)
                    Spacer(modifier = Modifier.height(AppTheme.spacing.level1))
                    BodyTexts.Level3(
                        text = userDetails.phone.ifEmpty {
                            userDetails.mail
                        },
                        color = AppTheme.colors.onBackground.copy(alpha = 0.4f)
                    )
                }
            }
            Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.End) {
                if (userDetails.prime) {
                    AppIcon(imageVector = Icons.Filled.Diamond, tint = AppTheme.colors.primary)
                }
                Spacer(modifier = Modifier.height(AppTheme.spacing.level1))
                AddToCommunityButton(addToCommunity = addToCommunity)
            }
        }
    }
    Spacer(modifier = Modifier.height(AppTheme.spacing.level2))
}


@Composable
fun AddToCommunityButton(addToCommunity: () -> Unit = {}) {
    AppIconButton(imageVector = Icons.Filled.GroupAdd, onClick = addToCommunity)
}


@Composable
fun InvitationReport() {
    Column(Modifier.padding(horizontal = AppTheme.spacing.level2)) {
        HeadingTexts.Level2(text = "Invite Report")
        Row(
            Modifier
                .fillMaxWidth()
                .padding(vertical = AppTheme.spacing.level2),
            horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2),
            verticalAlignment = Alignment.CenterVertically
        ) {
            InvitationReportCard(modifier = Modifier.weight(1f))
            InvitationReportCard(modifier = Modifier.weight(1f))
            InvitationReportCard(modifier = Modifier.weight(1f))
        }
    }
}


@Composable
fun InvitationReportCard(
    modifier: Modifier = Modifier,
    cardTitle: String = "Demo",
    cardValue: String = "$500"
) {
    AppCard(modifier = modifier) {
        Column(
            modifier = Modifier
                .padding(AppTheme.spacing.level2)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            LargeTexts.Level3(text = cardValue)
            Spacer(modifier = Modifier.height(AppTheme.spacing.level1))
            CaptionTexts.Level2(text = cardTitle)
        }
    }
}