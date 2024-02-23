package fit.asta.health.feature.profile.profile.ui.screens

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
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Male
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PregnantWoman
import androidx.compose.material.icons.filled.Subscriptions
import androidx.compose.material.icons.filled.Transgender
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.rounded.AddAPhoto
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.EditCalendar
import androidx.compose.material.icons.rounded.PrivacyTip
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.maxkeppeker.sheets.core.models.base.UseCaseState
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import fit.asta.health.common.utils.getImageModel
import fit.asta.health.data.profile.remote.model.getGenderName
import fit.asta.health.data.profile.remote.model.isFemale
import fit.asta.health.data.profile.remote.model.isMale
import fit.asta.health.data.profile.remote.model.isTrue
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.button.AppCheckBoxButton
import fit.asta.health.designsystem.molecular.button.AppIconButton
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.image.AppNetworkImage
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.feature.profile.profile.ui.components.BottomSheetGenderSelector
import fit.asta.health.feature.profile.profile.ui.components.BottomSheetTextField
import fit.asta.health.feature.profile.profile.ui.components.ClickableTextBox
import fit.asta.health.feature.profile.profile.ui.components.PageNavigationButtons
import fit.asta.health.feature.profile.profile.ui.state.UserProfileState
import fit.asta.health.resources.strings.R
import java.time.format.DateTimeFormatter
import java.util.Calendar

@ExperimentalMaterial3Api
@Composable
fun BasicDetailsScreen(
    userProfileState: UserProfileState,
) {
    val nameBottomSheetState = rememberModalBottomSheetState()
    val nameBottomSheetVisible = rememberSaveable { mutableStateOf(false) }

    val genderBottomSheetState = rememberModalBottomSheetState()
    val genderBottomSheetVisible = rememberSaveable { mutableStateOf(false) }

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
            userProfileState.openSheet(
                nameBottomSheetState,
                nameBottomSheetVisible,
            )
        }

        ClickableTextBox(
            label = "E-mail",
            value = userProfileState.basicDetailScreenState.email,
            leadingIcon = Icons.Default.Mail,
            trailingIcon = null
        )

        ClickableTextBox(
            label = "Gender",
            value = userProfileState.basicDetailScreenState.userGender.getGenderName()
                ?: "Select Gender",
            leadingIcon =
            if (userProfileState.basicDetailScreenState.userGender.isMale()) {
                Icons.Default.Male
            } else if (userProfileState.basicDetailScreenState.userGender.isFemale()) {
                if (userProfileState.basicDetailScreenState.isPregnant.isTrue()) {
                    Icons.Default.PregnantWoman
                } else if (userProfileState.basicDetailScreenState.onPeriod.isTrue()) {
                    Icons.Default.WaterDrop
                } else {
                    Icons.Default.Female
                }
            } else {
                Icons.Default.Transgender
            }
        ) {
            userProfileState.openSheet(
                genderBottomSheetState,
                genderBottomSheetVisible
            )
        }

        AgeSection(
            userProfileState,
            calendarUseCaseState
        )

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

        PageNavigationButtons(
            onNext = {
                userProfileState.currentPageIndex++
            }
        )

        Spacer(modifier = Modifier)


        //Dialogs
        BottomSheetTextField(
            isVisible = nameBottomSheetVisible.value,
            sheetState = nameBottomSheetState,
            label = "Enter your Name",
            text = userProfileState.basicDetailScreenState.userName,
            onDismissRequest = {
                userProfileState.closeSheet(
                    nameBottomSheetState,
                    nameBottomSheetVisible,
                )
            },
            onSaveClick = {
                userProfileState.basicDetailScreenState.saveName(it)
                userProfileState.closeSheet(
                    nameBottomSheetState,
                    nameBottomSheetVisible,
                )
            }
        )

        BottomSheetGenderSelector(
            isVisible = genderBottomSheetVisible.value,
            sheetState = genderBottomSheetState,
            gender = userProfileState.basicDetailScreenState.userGender,
            isPregnant = userProfileState.basicDetailScreenState.isPregnant,
            onPeriod = userProfileState.basicDetailScreenState.onPeriod,
            pregnancyWeek = userProfileState.basicDetailScreenState.userPregnancyWeek,
            onDismissRequest = {
                userProfileState.closeSheet(
                    genderBottomSheetState,
                    genderBottomSheetVisible
                )
            },
            onSaveClick = { _, _, _, _ ->

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
        value = userProfileState.basicDetailScreenState.userDob ?: "Select DOB",
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