package fit.asta.health.feature.profile.profile.ui

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Subscriptions
import androidx.compose.material.icons.rounded.AddAPhoto
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.EditCalendar
import androidx.compose.material.icons.rounded.PrivacyTip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.maxkeppeker.sheets.core.models.base.UseCaseState
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import fit.asta.health.common.utils.getImageModel
import fit.asta.health.common.utils.toStringFromResId
import fit.asta.health.data.profile.remote.model.BooleanIntTypes
import fit.asta.health.data.profile.remote.model.GenderTypes
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.button.AppCheckBoxButton
import fit.asta.health.designsystem.molecular.button.AppFilledButton
import fit.asta.health.designsystem.molecular.button.AppIconButton
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.image.AppNetworkImage
import fit.asta.health.designsystem.molecular.textfield.AppTextField
import fit.asta.health.designsystem.molecular.textfield.AppTextFieldType
import fit.asta.health.designsystem.molecular.textfield.AppTextFieldValidator
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.feature.profile.profile.ui.components.BottomSheetTextField
import fit.asta.health.feature.profile.profile.ui.components.ClickableTextBox
import fit.asta.health.feature.profile.profile.ui.state.UserProfileState
import fit.asta.health.feature.profile.profile.utils.GenderSelector
import fit.asta.health.feature.profile.profile.utils.TwoTogglesGroup
import fit.asta.health.resources.strings.R
import java.time.format.DateTimeFormatter
import java.util.Calendar

@ExperimentalMaterial3Api
@Composable
fun BasicDetailsScreen(
    userProfileState: UserProfileState,
) {
    val bottomSheetState = rememberModalBottomSheetState()
    val bottomSheetVisible = rememberSaveable { mutableStateOf(false) }
    val calendarUseCaseState = rememberUseCaseState()

    val imgLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
            userProfileState.basicDetailScreenState.profileImageLocalUri = uri
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
                uri = userProfileState.basicDetailScreenState.profileImageLocalUri,
                remoteUrl = userProfileState.basicDetailScreenState.profileImageUrl
            ),
            onUserProfileSelection = {
                imgLauncher.launch("image/*")
            },
            onProfilePicClear = {
                userProfileState.basicDetailScreenState.clearProfile()
            }
        )

        ClickableTextBox(
            label = "Name",
            value = userProfileState.basicDetailScreenState.userName,
            leadingIcon = Icons.Default.Person
        ) {
            userProfileState.basicDetailScreenState.openNameSheet(
                bottomSheetState,
                bottomSheetVisible
            )
        }

        ClickableTextBox(
            label = "E-mail",
            value = userProfileState.basicDetailScreenState.email,
            leadingIcon = Icons.Default.Mail,
            trailingIcon = null
        )

        GenderSection(userProfileState)

        AgeSection(userProfileState, calendarUseCaseState)

        ClickableTextBox(
            label = "Subscription",
            value = "Check your plan",
            leadingIcon = Icons.Default.Subscriptions,
            trailingIcon = Icons.Default.ArrowForward
        ) {
            userProfileState.navigateToSubscriptions()
        }

        ClickableTextBox(
            label = "Wallet",
            value = "Check your balance",
            leadingIcon = Icons.Default.AccountBalanceWallet,
            trailingIcon = Icons.Default.ArrowForward
        ) {
            userProfileState.navigateToWallet()
        }

        ClickableTextBox(
            label = "Orders",
            value = "Check order history",
            leadingIcon = Icons.Default.AccountBalanceWallet,
            trailingIcon = Icons.Default.ArrowForward
        ) {
            userProfileState.navigateToOrders()
        }

        AppFilledButton(
            textToShow = stringResource(R.string.next_button),
            modifier = Modifier
                .fillMaxWidth(),
            onClick = {
                userProfileState.currentPageIndex++
            }
        )

        Spacer(modifier = Modifier)

        BottomSheetTextField(
            isVisible = bottomSheetVisible.value,
            sheetState = bottomSheetState,
            label = "Enter your Name",
            text = userProfileState.basicDetailScreenState.userName,
            onDismissRequest = {
                userProfileState.basicDetailScreenState.closeNameSheet(
                    bottomSheetState,
                    bottomSheetVisible
                )
            },
            onSaveClick = {
                userProfileState.basicDetailScreenState.saveName(it)
                userProfileState.basicDetailScreenState.closeNameSheet(
                    bottomSheetState,
                    bottomSheetVisible
                )
            }
        )

        CalendarSection(userProfileState, calendarUseCaseState)
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun CalendarSection(
    userProfileState: UserProfileState,
    useCaseState: UseCaseState
) {
    CalendarDialog(
        state = useCaseState, selection = CalendarSelection.Date {
            userProfileState.basicDetailScreenState.userDob =
                it.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).toString()
            userProfileState.basicDetailScreenState.setAge(
                (userProfileState.basicDetailScreenState.calendar.get(Calendar.YEAR) - it.year)
            )
        },
        config = CalendarConfig(monthSelection = true, yearSelection = true)
    )
}

@Composable
private fun GenderSection(
    userProfileState: UserProfileState,
) {
    val focusManager = LocalFocusManager.current
    AppCard(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(AppTheme.spacing.level2)
        ) {
            GenderSelector(
                title = R.string.gender.toStringFromResId(),
                selectedOption = userProfileState.basicDetailScreenState.userGender ?: -1,
            ) {
                userProfileState.basicDetailScreenState.userGender = it
            }

            AnimatedVisibility(userProfileState.basicDetailScreenState.userGender == GenderTypes.FEMALE.gender) {
                Column {
                    TwoTogglesGroup(
                        selectionTypeText = stringResource(R.string.periodTitle_profile_creation),
                        selectedOption = userProfileState.basicDetailScreenState.onPeriod ?: -1,
                        onStateChange = { state ->
                            userProfileState.basicDetailScreenState.onPeriod = state
                        }
                    )
                    TwoTogglesGroup(
                        selectionTypeText = stringResource(R.string.pregnantTitle_profile_creation),
                        selectedOption = userProfileState.basicDetailScreenState.isPregnant ?: -1,
                        onStateChange = { state ->
                            userProfileState.basicDetailScreenState.isPregnant = state
                        }
                    )
                    AnimatedVisibility(userProfileState.basicDetailScreenState.isPregnant == BooleanIntTypes.YES.value) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = AppTheme.spacing.level2),
                            horizontalArrangement = Arrangement.Start
                        ) {
                            TitleTexts.Level4(
                                text = stringResource(id = R.string.pregnancyWeekInput_profile_creation),
                                color = AppTheme.colors.onTertiaryContainer
                            )
                        }
                        Spacer(modifier = Modifier.height(AppTheme.spacing.level2))
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = AppTheme.spacing.level2)
                        ) {
                            AppTextField(
                                modifier = Modifier.fillMaxWidth(),
                                value = userProfileState.basicDetailScreenState.userPregnancyWeek
                                    ?: "",
                                onValueChange = {
                                    userProfileState.basicDetailScreenState.userPregnancyWeek = it
                                },
                                appTextFieldType = AppTextFieldValidator(
                                    AppTextFieldType.Custom(
                                        isInvalidLogic = { _, _ ->
                                            userProfileState.basicDetailScreenState.userPregnancyWeekErrorMessage != null
                                        },
                                        getErrorMessageLogic = { _, _ ->
                                            userProfileState.basicDetailScreenState.userPregnancyWeekErrorMessage
                                                ?: ""
                                        }
                                    )
                                ),
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Number,
                                    imeAction = ImeAction.Done
                                ),
                                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                                colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = AppTheme.colors.onSurface)
                            )
                        }
                    }
                }
            }
        }
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
private fun AgeSection(
    userProfileState: UserProfileState,
    calendarUseCaseState: UseCaseState
) {
    val ageColorSelection =
        if (userProfileState.basicDetailScreenState.userAgeErrorMessage == null) {
            AppTheme.colors.onSurface
        } else {
            AppTheme.colors.error
        }

    ClickableTextBox(
        label = "DOB",
        value = userProfileState.basicDetailScreenState.userAge?.toString() ?: "",
        leadingIcon = Icons.Rounded.EditCalendar
    ) {
        calendarUseCaseState.show()
    }

    userProfileState.basicDetailScreenState.userAgeErrorMessage?.let {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BodyTexts.Level1(text = it, color = AppTheme.colors.error)
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