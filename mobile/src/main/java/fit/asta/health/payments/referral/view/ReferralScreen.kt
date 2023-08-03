package fit.asta.health.payments.referral.view

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import fit.asta.health.R
import fit.asta.health.common.jetpack.dashedBorder
import fit.asta.health.common.ui.components.generic.AppErrorScreen
import fit.asta.health.common.ui.components.generic.AppTopBar
import fit.asta.health.common.ui.components.generic.LoadingAnimation
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.common.utils.copyTextToClipboard
import fit.asta.health.common.utils.getErrorMessage
import fit.asta.health.common.utils.getImgUrl
import fit.asta.health.common.utils.shareReferralCode
import fit.asta.health.payments.referral.model.ApplyCodeResponse
import fit.asta.health.payments.referral.model.ReferralDataResponse
import fit.asta.health.payments.referral.model.UserDetails

@Composable
fun ReferralScreen(
    referralDataState: ResponseState<ReferralDataResponse>,
    applyCodeState: ResponseState<ApplyCodeResponse>,
    onCheckRefereeData: (code: String) -> Unit,
    onBackPress: () -> Unit,
    onTryAgain: () -> Unit
) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            AppTopBar(title = "Referral", onBack = onBackPress)
        },
    ) { paddingValues ->
        when (referralDataState) {
            is ResponseState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    LoadingAnimation()
                }
            }

            is ResponseState.Error -> {
                AppErrorScreen(
                    modifier = Modifier.padding(paddingValues),
                    desc = "Something went wrong!",
                    isInternetError = false
                ) {
                    onTryAgain()
                }
            }

            is ResponseState.Success -> {
                LazyColumn(
                    modifier = Modifier
                        .padding(paddingValues)
                ) {

                    item { ReferralCard(referralDataState.data.data.referralCode.refCode) }

                    item {
                        if (referralDataState.data.data.referredByUsersDetails == null) {
                            val showLoading = remember {
                                mutableStateOf(false)
                            }
                            val enabled = remember {
                                mutableStateOf(true)
                            }
                            when (applyCodeState) {
                                is ResponseState.Success -> {
                                    showLoading.value = false
                                    LaunchedEffect(applyCodeState.data) {
                                        Toast.makeText(
                                            context,
                                            "Referral code applied!",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                    ReferralCustomCard(title = "Referred by:") {
                                        ReferredUserItem(
                                            modifier = Modifier
                                                .padding(spacing.medium),
                                            user = applyCodeState.data.data!!
                                        )
                                    }
                                }

                                else -> {
                                    LaunchedEffect(applyCodeState) {
                                        if (applyCodeState is ResponseState.ErrorResponse) {
                                            Toast.makeText(
                                                context,
                                                context.getErrorMessage(applyCodeState.code),
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            showLoading.value = false
                                            enabled.value = true
                                        }
                                    }
                                    if (showLoading.value) {
                                        LoadingAnimation()
                                    }

                                    Row(
                                        Modifier
                                            .padding(spacing.medium)
                                            .fillMaxWidth()
                                    ) {
                                        var code by rememberSaveable {
                                            mutableStateOf("")
                                        }
                                        TextField(
                                            modifier = Modifier.weight(1f),
                                            value = code,
                                            onValueChange = {
                                                if (it.length <= 8) code = it
                                            }
                                        )
                                        Button(
                                            enabled = code.length == 8 && enabled.value,
                                            onClick = {
                                                onCheckRefereeData(code)
                                                showLoading.value = true
                                                enabled.value = false
                                            }
                                        ) {
                                            Text(text = "Check")
                                        }
                                    }
                                }
                            }
                        } else {
                            ReferralCustomCard(title = "Referred by:") {
                                ReferredUserItem(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(spacing.medium),
                                    user = referralDataState.data.data.referredByUsersDetails
                                )
                            }
                        }
                    }

                    val referredUsers = referralDataState.data.data.referredUsers
                    referredUsers?.let {
                        items(it) { user ->
                            Spacer(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(spacing.medium)
                                    .height(1.dp)
                                    .background(MaterialTheme.colorScheme.surfaceVariant)
                            )
                            ReferredUserItem(
                                user = user
                            )
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
            .padding(spacing.medium)
            .background(MaterialTheme.colorScheme.surface),
        shape = MaterialTheme.shapes.extraLarge,
    ) {
        Text(
            modifier = Modifier
                .padding(top = spacing.medium, start = spacing.medium),
            text = title,
            textAlign = TextAlign.Start
        )

        content()
    }
}

@Composable
private fun ReferralCard(code: String) {
    val context = LocalContext.current
    Column {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacing.medium)
                .clickable {
                    context.shareReferralCode(code)
                }
                .paint(
                    painterResource(id = R.drawable.error_404)
                ),
            shape = MaterialTheme.shapes.extraLarge,
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(spacing.large),
                text = stringResource(id = R.string.refer_and_earn),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = spacing.large, start = spacing.large, end = spacing.large),
                text = stringResource(id = R.string.refer_invite_text),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium
            )
            Icon(
                modifier = Modifier
                    .clip(CircleShape)
                    .padding(spacing.medium)
                    .align(Alignment.CenterHorizontally),
                imageVector = Icons.Default.Share,
                contentDescription = "",
                tint = MaterialTheme.colorScheme.secondaryContainer
            )

        }
        Box(
            modifier = Modifier
                .padding(spacing.medium)
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
                modifier = Modifier.padding(spacing.medium),
                text = stringResource(id = R.string.code_side_text) + code,
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
                .padding(spacing.small)
                .clip(CircleShape),
            painter = rememberAsyncImagePainter(
                model = getImgUrl(url = user.pic),
                placeholder = painterResource(
                    id = R.drawable.ic_person
                )
            ), contentDescription = "Profile"
        )
        Text(text = user.name)
    }
}