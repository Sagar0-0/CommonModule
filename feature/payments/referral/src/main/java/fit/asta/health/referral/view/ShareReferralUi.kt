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
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import fit.asta.health.designsystem.molecular.AppErrorScreen
import fit.asta.health.designsystem.molecular.AppTopBar
import fit.asta.health.designsystem.molecular.animations.AppDotTypingAnimation
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

    Scaffold(
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
                AppErrorScreen(
                    modifier = Modifier.padding(paddingValues),
                    desc = "Something went wrong!",
                    isInternetError = false
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
                                        .padding(AppTheme.spacing.level3),
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
                                            .padding(AppTheme.spacing.level3),
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
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(AppTheme.spacing.level3)
            .background(MaterialTheme.colorScheme.surface),
        shape = MaterialTheme.shapes.extraLarge,
    ) {
        Text(
            modifier = Modifier
                .padding(top = AppTheme.spacing.level3, start = AppTheme.spacing.level3),
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
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(AppTheme.spacing.level3)
                .clickable {
                    shareReferralCode(code)
                },
            shape = MaterialTheme.shapes.extraLarge,
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(AppTheme.spacing.level5),
                text = stringResource(id = StringR.string.refer_and_earn),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(
                        bottom = AppTheme.spacing.level5,
                        start = AppTheme.spacing.level5,
                        end = AppTheme.spacing.level5
                    ),
                text = stringResource(id = StringR.string.refer_invite_text),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium
            )
            Icon(
                modifier = Modifier
                    .clip(CircleShape)
                    .padding(AppTheme.spacing.level3)
                    .align(Alignment.CenterHorizontally),
                imageVector = Icons.Default.Share,
                contentDescription = "",
                tint = MaterialTheme.colorScheme.secondaryContainer
            )

        }
        Box(
            modifier = Modifier
                .padding(AppTheme.spacing.level3)
                .align(Alignment.CenterHorizontally)
                .dashedBorder(
                    width = 1.dp,
                    radius = 1.dp,
                    color = MaterialTheme.colorScheme.onBackground
                )
                .background(MaterialTheme.colorScheme.secondaryContainer.copy(0.5f))
                .clickable {
                    context.copyTextToClipboard(
                        code
                    )
                }
        ) {
            Text(
                modifier = Modifier.padding(AppTheme.spacing.level3),
                text = stringResource(id = StringR.string.code_side_text) + code,
                style = MaterialTheme.typography.labelLarge,
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
            .clip(MaterialTheme.shapes.extraLarge)
            .background(if (user.prime) Color.Green else Color.Red),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .padding(AppTheme.spacing.level2)
                .clip(CircleShape),
            painter = rememberAsyncImagePainter(
                model = getImgUrl(url = user.pic),
                placeholder = painterResource(
                    id = DrawR.drawable.ic_person
                )
            ), contentDescription = "Profile"
        )
        Text(text = user.name)
    }
}