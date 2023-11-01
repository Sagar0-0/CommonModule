package fit.asta.health.referral.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.copyTextToClipboard
import fit.asta.health.common.utils.getImgUrl
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.atomic.modifier.dashedBorder
import fit.asta.health.designsystem.molecular.AppNonInternetErrorScreen
import fit.asta.health.designsystem.molecular.animations.AppDotTypingAnimation
import fit.asta.health.designsystem.molecular.background.AppScaffold
import fit.asta.health.designsystem.molecular.background.AppTopBar
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.referral.remote.model.ReferralDataResponse
import fit.asta.health.referral.remote.model.UserDetails
import fit.asta.health.resources.drawables.R as DrawR
import fit.asta.health.resources.strings.R as StringR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShareReferralUi(
    referralDataState: UiState<ReferralDataResponse>,
    shareReferralCode: (String) -> Unit,
    onBackPress: () -> Unit,
    onTryAgain: () -> Unit
) {
    AppScaffold(
        topBar = {
            AppTopBar(title = "Referral", onBack = onBackPress)
        },
    ) { paddingValues ->
        when (referralDataState) {
            is UiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    AppDotTypingAnimation()
                }
            }

            is UiState.ErrorMessage -> {
                AppNonInternetErrorScreen(
                    modifier = Modifier.padding(paddingValues),
                    issueDescription = "Something went wrong!",
                ) {
                    onTryAgain()
                }
            }

            is UiState.Success -> {
                LazyColumn(
                    modifier = Modifier
                        .padding(paddingValues)
                ) {

                    item {
                        ReferralCard(
                            referralDataState.data.referralCode.refCode,
                            shareReferralCode
                        )
                    }

                    item {
                        referralDataState.data.referredByUsersDetails?.let {
                            ReferralCustomCard(title = "Referred by:") {
                                ReferredUserItem(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(AppTheme.spacing.level2),
                                    user = it
                                )
                            }
                        }
                    }

                    item {
                        referralDataState.data.referredUsers?.let {
                            ReferralCustomCard(title = "Referrals by you:") {
                                it.forEach { user ->
                                    ReferredUserItem(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(AppTheme.spacing.level2),
                                        user = user
                                    )
                                }
                            }
                        }
                    }
                }
            }

            else -> {}
        }

    }
}

@Composable
fun ReferralCustomCard(title: String, content: @Composable () -> Unit) {
    AppCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(AppTheme.spacing.level2),
    ) {
        TitleTexts.Level2(
            modifier = Modifier
                .padding(top = AppTheme.spacing.level2, start = AppTheme.spacing.level2),
            text = title,
            textAlign = TextAlign.Start
        )

        content()
    }
}

@Composable
private fun ReferralCard(code: String, shareReferralCode: (String) -> Unit) {
    val context = LocalContext.current
    Column {
        AppCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(AppTheme.spacing.level2)
                .clickable {
                    shareReferralCode(code)
                },
        ) {
            TitleTexts.Level2(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(AppTheme.spacing.level4),
                text = stringResource(id = StringR.string.refer_and_earn),
                textAlign = TextAlign.Center
            )
            TitleTexts.Level2(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(
                        bottom = AppTheme.spacing.level4,
                        start = AppTheme.spacing.level4,
                        end = AppTheme.spacing.level4
                    ),
                text = stringResource(id = StringR.string.refer_invite_text),
                textAlign = TextAlign.Center,
            )
            AppIcon(
                modifier = Modifier
                    .clip(CircleShape)
                    .padding(AppTheme.spacing.level2)
                    .align(Alignment.CenterHorizontally),
                imageVector = Icons.Default.Share,
                contentDescription = ""
            )

        }
        Box(
            modifier = Modifier
                .padding(AppTheme.spacing.level2)
                .align(Alignment.CenterHorizontally)
                .dashedBorder(
                    width = 1.dp,
                    radius = 1.dp,
                    color = AppTheme.colors.onBackground
                )
                .clickable {
                    context.copyTextToClipboard(
                        code
                    )
                }
        ) {
            TitleTexts.Level2(
                modifier = Modifier.padding(AppTheme.spacing.level2),
                text = stringResource(id = StringR.string.code_side_text) + code,
                textAlign = TextAlign.Center
            )
        }
    }


}

@Composable
private fun ReferredUserItem(
    modifier: Modifier = Modifier,
    user: UserDetails
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(if (user.prime) Color.Green else Color.Red),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .padding(AppTheme.spacing.level1)
                .clip(CircleShape),
            painter = rememberAsyncImagePainter(
                model = getImgUrl(url = user.pic),
                placeholder = painterResource(
                    id = DrawR.drawable.ic_person
                )
            ), contentDescription = "Profile"
        )
        TitleTexts.Level2(text = user.name)
    }
}