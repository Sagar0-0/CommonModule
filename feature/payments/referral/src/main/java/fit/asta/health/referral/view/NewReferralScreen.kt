package fit.asta.health.referral.view

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Diamond
import androidx.compose.material.icons.filled.GroupAdd
import androidx.compose.material.icons.filled.Link
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.background.AppSurface
import fit.asta.health.designsystem.molecular.background.AppTopBar
import fit.asta.health.designsystem.molecular.button.AppFilledButton
import fit.asta.health.designsystem.molecular.button.AppIconButton
import fit.asta.health.designsystem.molecular.button.AppTextButton
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.image.AppLocalImage
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.CaptionTexts
import fit.asta.health.designsystem.molecular.texts.HeadingTexts
import fit.asta.health.designsystem.molecular.texts.LargeTexts
import fit.asta.health.resources.drawables.R

@OptIn(ExperimentalMaterial3Api::class)
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
fun NewReferralDesign(shareRefLink: () -> Unit = {}, copyRefCode: () -> Unit = {}) {

    AppTheme {
        AppSurface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AppTopBar(
                    title = "Refer and Earn",
                    backIcon = Icons.Filled.ArrowBackIosNew,
                    onBack = {})
                Spacer(modifier = Modifier.height(16.dp))
                ReferralImg()
                Spacer(modifier = Modifier.height(16.dp))
                ShareRefBtn(shareRefLink)
                LargeTexts.Level2(
                    text = "OR",
                    color = AppTheme.colors.onSurfaceVariant,
                )
                CopyRefCodeCard(copyRefCode)
                Spacer(modifier = Modifier.height(16.dp))
                InvitationReport()
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    HeadingTexts.Level2(text = "You've invited...")
                }
                Spacer(modifier = Modifier.height(24.dp))
                InvitedUserList()
            }
        }
    }
}

@Composable
fun ReferralImg(
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
fun ShareRefBtn(shareRefLink: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
    ) {
        AppFilledButton(
            textToShow = "Share your link",
            trailingIcon = Icons.Filled.Link, onClick = shareRefLink
        )
    }
}

@Composable
fun CopyRefCodeCard(copyRefCode: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(vertical = AppTheme.spacing.level2),
        horizontalArrangement = Arrangement.Center
    ) {
        AppCard {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                HeadingTexts.Level1(
                    text = "QWE123",
                    modifier = Modifier.padding(AppTheme.spacing.level2),
                    textAlign = TextAlign.Center,
                    color = AppTheme.colors.primary
                )
                AppTextButton(
                    textToShow = "Copy",
                    leadingIcon = Icons.Filled.ContentCopy,
                    onClick = copyRefCode
                )
            }
        }
    }
}

@Composable
fun InvitedUserList(
    userPofImg: Int = R.drawable.ic_person,
    userName: String = "Demo Name",
    userPhone: String = "1000000000",
    userEmail: String = "demo@gmail.com",
    isProMember: Boolean = true,
    addToCommunity: () -> Unit = {}
) {
    AppCard(modifier = Modifier.padding(horizontal = 16.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row {
                AppLocalImage(
                    painter = painterResource(id = userPofImg),
                    contentDescription = "Profile",
                    modifier = Modifier
                        .size(AppTheme.boxSize.level6)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    HeadingTexts.Level3(text = userName)
                    Spacer(modifier = Modifier.height(AppTheme.spacing.level1))
                    BodyTexts.Level3(
                        text = userPhone.ifEmpty {
                            userEmail
                        },
                        color = AppTheme.colors.onBackground.copy(alpha = 0.4f)
                    )
                }
            }
            Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.End) {
                if (isProMember) {
                    AppIcon(imageVector = Icons.Filled.Diamond, tint = AppTheme.colors.primary)
                }
                Spacer(modifier = Modifier.height(AppTheme.spacing.level1))
                AddToCommunityBtn(addToCommunity = addToCommunity)
            }
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun AddToCommunityBtn(addToCommunity: () -> Unit = {}) {
    AppIconButton(imageVector = Icons.Filled.GroupAdd, onClick = addToCommunity)
}

@Composable
fun InvitationReport() {
    Column(Modifier.padding(horizontal = 16.dp)) {
        HeadingTexts.Level2(text = "Invite Report")
        Row(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2),
            verticalAlignment = Alignment.CenterVertically
        ) {
            InvRepCard(modifier = Modifier.weight(1f))
            InvRepCard(modifier = Modifier.weight(1f))
            InvRepCard(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun InvRepCard(
    modifier: Modifier = Modifier,
    cardTitle: String = "Demo",
    cardValue: String = "$500"
) {
    AppCard(modifier = modifier) {
        Column(
            modifier = Modifier
                .padding(16.dp)
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