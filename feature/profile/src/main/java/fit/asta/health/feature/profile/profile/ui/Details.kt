package fit.asta.health.feature.profile.profile.ui

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.rounded.AddAPhoto
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.PrivacyTip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.getImageModel
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.atomic.modifier.appShimmerAnimation
import fit.asta.health.designsystem.molecular.AppUiStateHandler
import fit.asta.health.designsystem.molecular.button.AppCheckBoxButton
import fit.asta.health.designsystem.molecular.button.AppFilledButton
import fit.asta.health.designsystem.molecular.button.AppIconButton
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.image.AppNetworkImage
import fit.asta.health.designsystem.molecular.textfield.AppOutlinedTextField
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.CaptionTexts
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.feature.profile.profile.ui.state.UserProfileState
import fit.asta.health.resources.strings.R
import fit.asta.health.subscription.remote.model.UserSubscribedPlan
import fit.asta.health.wallet.remote.model.WalletResponse

@ExperimentalMaterial3Api
@Composable
fun DetailsScreen(
    userProfileState: UserProfileState,
    walletDataState: UiState<WalletResponse>,
    subscriptionDataState: UiState<UserSubscribedPlan>,
) {
    val imgLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
            userProfileState.profileImageLocalUri = uri
            userProfileState.isImageCropperVisible = true
        }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = AppTheme.spacing.level2),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)
    ) {

        Spacer(modifier = Modifier)

        UserCircleImage(
            model = getImageModel(
                uri = userProfileState.profileImageLocalUri,
                remoteUrl = userProfileState.profileImageUrl
            ),
            onUserProfileSelection = {
                imgLauncher.launch("image/*")
            },
            onProfilePicClear = {
                userProfileState.clearProfile()
            }
        )

        AppOutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = userProfileState.userName,
            onValueChange = {
                userProfileState.userName = it
            },
            label = stringResource(R.string.name_profile_creation),
            singleLine = true
        )

        TitleTexts.Level2(
            modifier = Modifier
                .fillMaxWidth(),
            text = userProfileState.email,
            textAlign = TextAlign.Center
        )

        AppUiStateHandler(
            uiState = subscriptionDataState,
            onLoading = {
                AppCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(AppTheme.cardHeight.level3)
                        .appShimmerAnimation(true)
                ) { }
            }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(AppTheme.spacing.level2),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CaptionTexts.Level2(text = "Current active plan:")
                CaptionTexts.Level2(text = it.ttl)
            }
        }

        AppUiStateHandler(
            uiState = walletDataState,
            onLoading = {
                AppCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(AppTheme.cardHeight.level3)
                        .appShimmerAnimation(true)
                ) { }
            }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(AppTheme.spacing.level2),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CaptionTexts.Level2(text = "Wallet balance:")
                CaptionTexts.Level2(text = "${it.walletData.money}/${it.walletData.points}")
            }
        }

        PrivacyAndUserConsent()

        AppFilledButton(
            textToShow = stringResource(R.string.next_button),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = AppTheme.spacing.level4),
            shape = CircleShape,
            onClick = {
                userProfileState.currentPageIndex++
            }
        )

        Spacer(modifier = Modifier)
    }
}

@Composable
fun PrivacyAndUserConsent() {

    val checkedState = remember { mutableStateOf(false) }

    Column(
        Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            AppIcon(
                imageVector = Icons.Rounded.PrivacyTip,
                contentDescription = "App Privacy",
                tint = AppTheme.colors.primary
            )
            Spacer(modifier = Modifier.width(AppTheme.spacing.level2))
            Column {
                TitleTexts.Level2(text = stringResource(R.string.privacy_statement_title))
                Spacer(modifier = Modifier.height(AppTheme.spacing.level0))
                BodyTexts.Level2(text = stringResource(R.string.privacy_statement))
            }
        }
        Spacer(modifier = Modifier.height(AppTheme.spacing.level2))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            AppCheckBoxButton(
                checked = checkedState.value,
                onCheckedChange = { checkedState.value = it },
                modifier = Modifier.size(AppTheme.imageSize.level3)
            )
            Spacer(modifier = Modifier.width(AppTheme.spacing.level2))
            BodyTexts.Level2(text = stringResource(R.string.user_consent))
        }
    }
}

@Composable
fun UserCircleImage(
    modifier: Modifier = Modifier,
    model: String,
    onUserProfileSelection: () -> Unit,
    onProfilePicClear: () -> Unit,
) {

    val isImgNotAvail = model.isEmpty()

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        AppNetworkImage(
            model = model,
            contentDescription = "User Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(AppTheme.customSize.level11)
                .clip(CircleShape)
                .border(
                    border = BorderStroke(
                        width = 4.dp, color = AppTheme.colors.primary
                    ), shape = CircleShape
                ),
            errorImage = rememberVectorPainter(image = Icons.Filled.Person)
        )
        if (!isImgNotAvail) {
            DeleteImageButton(onProfilePicClear, modifier = Modifier.align(Alignment.TopEnd))
        }
        EditProfileImageButton(
            isImgNotAvail,
            onUserProfileSelection,
            modifier = Modifier.align(alignment = Alignment.BottomEnd)
        )
    }
}

@Composable
fun DeleteImageButton(onProfilePicClear: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.Start, modifier = modifier
    ) {
        AppIconButton(
            imageVector = Icons.Filled.Delete,
            iconTint = AppTheme.colors.error,
            onClick = onProfilePicClear,
        )
    }
}

@Composable
fun EditProfileImageButton(
    isImgNotAvail: Boolean,
    onUserProfileSelection: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val editIcon = if (isImgNotAvail) Icons.Rounded.AddAPhoto else Icons.Rounded.Edit
    Row(
        horizontalArrangement = Arrangement.End, modifier = modifier
    ) {
        AppIconButton(
            imageVector = editIcon,
            iconTint = LocalContentColor.current.copy(alpha = LocalContentAlpha.current),
            onClick = onUserProfileSelection
        )
    }
}