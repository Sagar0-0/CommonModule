package fit.asta.health.feature.profile.profile.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.Icons.AutoMirrored.Filled
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Male
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PregnantWoman
import androidx.compose.material.icons.filled.Subscriptions
import androidx.compose.material.icons.filled.Transgender
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.rounded.AddAPhoto
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.EditCalendar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.maxkeppeker.sheets.core.models.base.UseCaseState
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import fit.asta.health.common.utils.getImageModel
import fit.asta.health.data.profile.remote.model.getGenderName
import fit.asta.health.data.profile.remote.model.isFemale
import fit.asta.health.data.profile.remote.model.isMale
import fit.asta.health.data.profile.remote.model.isTrue
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.button.AppIconButton
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.feature.profile.profile.ui.components.BottomSheetGenderSelector
import fit.asta.health.feature.profile.profile.ui.components.BottomSheetTextField
import fit.asta.health.feature.profile.profile.ui.components.ClickableTextBox
import fit.asta.health.feature.profile.profile.ui.components.DatePicker
import fit.asta.health.feature.profile.profile.ui.components.PageNavigationButtons
import fit.asta.health.feature.profile.profile.ui.components.ProfileImagePicker
import fit.asta.health.feature.profile.profile.ui.state.UserProfileState
import java.time.format.DateTimeFormatter
import java.util.Calendar

@Composable
fun BasicDetailsScreen(
    userProfileState: UserProfileState,
) {
    val nameBottomSheetState = rememberModalBottomSheetState()
    val nameBottomSheetVisible = rememberSaveable { mutableStateOf(false) }

    val genderBottomSheetState = rememberModalBottomSheetState()
    val genderBottomSheetVisible = rememberSaveable { mutableStateOf(false) }

    val calendarUseCaseState = rememberUseCaseState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = AppTheme.spacing.level2),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)
    ) {

        Spacer(modifier = Modifier)

        ProfileImagePicker(
            model = getImageModel(
                uri = userProfileState.basicDetailScreenState.profileImageLocalUri,
                remoteUrl = userProfileState.basicDetailScreenState.profileImageUrl
            ),
            onProfilePicClear = {
                userProfileState.basicDetailScreenState.clearProfile()
            },
            onLauncherResult = {
                userProfileState.basicDetailScreenState.profileImageLocalUri = it
                userProfileState.basicDetailScreenState.isImageCropperVisible = true
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
            trailingIcon = Filled.ArrowForward
        ) {
            userProfileState.navigateToSubscriptions()
        }

        ClickableTextBox(
            label = "Wallet",
            value = "Check your balance",
            leadingIcon = Icons.Default.AccountBalanceWallet,
            trailingIcon = Filled.ArrowForward
        ) {
            userProfileState.navigateToWallet()
        }

        ClickableTextBox(
            label = "Refer and Earn",
            value = "Refer your Friend",
            leadingIcon = Icons.Default.MonetizationOn,
            trailingIcon = Filled.ArrowForward
        ) {
            userProfileState.navigateToReferral()
        }

        ClickableTextBox(
            label = "Orders",
            value = "Check order history",
            leadingIcon = Icons.Default.AccountBalanceWallet,
            trailingIcon = Filled.ArrowForward
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
            onSaveClick = { gender, isPregnant, onPeriod, pregnancyWeek ->
                userProfileState.basicDetailScreenState.saveGender(
                    gender = gender,
                    isPregnant = isPregnant,
                    onPeriod = onPeriod,
                    pregnancyWeek = pregnancyWeek
                )
                userProfileState.closeSheet(
                    genderBottomSheetState,
                    genderBottomSheetVisible
                )
            }
        )

        DatePicker(calendarUseCaseState) {
            userProfileState.basicDetailScreenState.saveDob(
                it.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).toString(),
                (userProfileState.basicDetailScreenState.calendar.get(Calendar.YEAR) - it.year)
            )
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
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    val editIcon = if (isImgNotAvail) Icons.Rounded.AddAPhoto else Icons.Rounded.Edit
    Row(
        horizontalArrangement = Arrangement.End, modifier = modifier
    ) {
        AppIconButton(
            imageVector = editIcon,
            iconTint = LocalContentColor.current.copy(alpha = LocalContentAlpha.current),
            onClick = onClick
        )
    }
}