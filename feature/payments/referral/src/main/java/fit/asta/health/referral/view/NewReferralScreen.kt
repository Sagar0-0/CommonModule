package fit.asta.health.referral.view

import android.content.res.Configuration
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.GroupAdd
import androidx.compose.material.icons.filled.Link
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.components.generic.AppCard
import fit.asta.health.designsystem.molecular.button.AppFilledButton
import fit.asta.health.designsystem.molecular.button.AppTextButton
import fit.asta.health.designsystem.molecular.image.AppLocalImage
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.CaptionTexts
import fit.asta.health.designsystem.molecular.texts.HeadingTexts
import fit.asta.health.designsystem.molecular.texts.LargeTexts
import fit.asta.health.designsystem.molecular.texts.TitleTexts
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
fun NewReferralDesign() {

    AppTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TopAppBar(title = { TitleTexts.Level1(text = "Refer and Earn") }, navigationIcon = {
                    Icon(
                        imageVector = Icons.Filled.ArrowBackIosNew, contentDescription = "Go Back"
                    )
                })
                Spacer(modifier = Modifier.height(16.dp))
                AppLocalImage(
                    painter = painterResource(id = R.drawable.ref_ed_2),
                    modifier = Modifier.aspectRatio(AppTheme.aspectRatio.common),
                    contentScale = ContentScale.Fit
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
                ) {
                    AppFilledButton(
                        textToShow = "Share your link",
                        trailingIcon = Icons.Filled.Link
                    ) {

                    }
                }
                LargeTexts.Level2(
                    text = "OR",
                    color = AppTheme.colors.onSurfaceVariant,
                )
                TitleTexts.Level4(text = "Share with your Referral Code:")
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(AppTheme.spacing.level3),
                    horizontalArrangement = Arrangement.Center
                ) {
                    AppCard {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            HeadingTexts.Level1(
                                text = "QWE123",
                                modifier = Modifier.padding(AppTheme.spacing.level3),
                                textAlign = TextAlign.Center,
                                color = AppTheme.colors.primary
                            )
                            Button(
                                onClick = { /*TODO*/ },
                                shape = RoundedCornerShape(12.dp),
                                contentPadding = PaddingValues(
                                    start = AppTheme.spacing.level4,
                                    top = AppTheme.spacing.level2,
                                    end = AppTheme.spacing.level4,
                                    bottom = AppTheme.spacing.level2
                                )
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(vertical = 24.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.ContentCopy,
                                        contentDescription = "Copy",
                                        modifier = Modifier.padding(horizontal = AppTheme.spacing.level1)
                                    )
                                    CaptionTexts.Level1(
                                        text = "Copy", color = AppTheme.colors.onPrimary
                                    )
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Divider(modifier = Modifier.padding(horizontal = 8.dp))
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    HeadingTexts.Level2(text = "You've invited...")
                }
                Spacer(modifier = Modifier.height(24.dp))
                SendReminderList()
                SendReminderList()
                SendReminderList()
                SendReminderList()
                SendReminderList()
                SendReminderList()
                SendReminderList()
                JoinedUserList()
                JoinedUserList()
                JoinedUserList()
                JoinedUserList()
                ExpiredUserList()
                ExpiredUserList()
                ExpiredUserList()
            }
        }
    }

}

@Composable
fun JoinedUserList() {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                HeadingTexts.Level3(text = "Naman Yadav")
                BodyTexts.Level3(
                    text = "Sent on Oct 04, 2023",
                    color = AppTheme.colors.onBackground.copy(alpha = 0.4f)
                )
            }
            JoinedUser()
        }
        Spacer(modifier = Modifier.height(16.dp))
        Divider(modifier = Modifier.padding(horizontal = 16.dp))
    }
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun SendReminderList() {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                HeadingTexts.Level3(text = "Naman Yadav")
                BodyTexts.Level3(
                    text = "Sent on Oct 04, 2023",
                    color = AppTheme.colors.onBackground.copy(alpha = 0.4f)
                )
            }
            SendReminder()
        }
        Spacer(modifier = Modifier.height(16.dp))
        Divider(modifier = Modifier.padding(horizontal = 16.dp))
    }
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun ExpiredUserList() {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                HeadingTexts.Level3(text = "Naman")
                BodyTexts.Level3(
                    text = "Sent on Oct 04, 2023",
                    color = AppTheme.colors.onBackground.copy(alpha = 0.4f)
                )
            }
            ExpiredUser()
        }
        Spacer(modifier = Modifier.height(16.dp))
        Divider(modifier = Modifier.padding(horizontal = 16.dp))
    }
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun SendReminder() {
    Column {
        BodyTexts.Level3(
            text = "Expires in 28d", color = AppTheme.colors.onBackground.copy(alpha = 0.4f)
        )
        CaptionTexts.Level1(text = "Send Reminder",
            color = AppTheme.colors.primary,
            modifier = Modifier.clickable { })
    }
}

@Composable
fun JoinedUser() {
    AppTextButton(textToShow = "Add to Community", trailingIcon = Icons.Filled.GroupAdd) {

    }
}

@Composable
fun ExpiredUser() {
    Column {
        BodyTexts.Level2(
            text = "Expired", color = AppTheme.colors.onBackground.copy(alpha = 0.4f)
        )
        CaptionTexts.Level1(
            text = "Send Again",
            color = AppTheme.colors.primary,
            modifier = Modifier.clickable { })
    }
}
