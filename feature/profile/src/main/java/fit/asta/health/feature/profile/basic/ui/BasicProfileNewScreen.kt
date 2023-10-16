package fit.asta.health.feature.profile.basic.ui

import android.content.res.Configuration
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CameraEnhance
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Link
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.background.AppScaffold
import fit.asta.health.designsystem.molecular.background.AppTopBar
import fit.asta.health.designsystem.molecular.button.AppFilledButton
import fit.asta.health.designsystem.molecular.button.AppIconButton
import fit.asta.health.designsystem.molecular.button.AppOutlinedButton
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.image.AppLocalImage
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.CaptionTexts
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.resources.drawables.R
import fit.asta.otpfield.OTPInput
import fit.asta.otpfield.configuration.OTPCellConfiguration
import fit.asta.otpfield.configuration.OTPConfigurations

@Preview("Light Button")
@Preview(
    name = "Dark Button", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true
)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicProfileNewScreen() {

    var profileImageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val imagePickerLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                profileImageUri = it
            }
        }

    AppTheme {
        AppScaffold(topBar = {
            AppTopBar(title = "Create Basic Profile")
        }, content = { paddingValues ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState()),
            ) {
                CircularImageWithIconButton(onClick = { imagePickerLauncher.launch("image/*") })
                TextBlock()
                EmailBlock()
                PhoneBlock()
                Spacer(modifier = Modifier.height(AppTheme.spacing.level2))
                OTPBlock()
                Spacer(modifier = Modifier.height(AppTheme.spacing.level2))
                ReferBlock()
                Spacer(modifier = Modifier.height(AppTheme.spacing.level2))
                GenerateButton(
                    textToShow = "Create your Profile",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = AppTheme.spacing.level2)
                )
            }
        })
    }

}

@Composable
fun CircularImageWithIconButton(modifier: Modifier = Modifier, onClick: () -> Unit = {}) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = AppTheme.spacing.level2)
    ) {
        val (image, button) = createRefs()

        AppLocalImage(painter = painterResource(id = R.drawable.ic_person),
            contentDescription = "Profile",
            modifier = Modifier
                .size(AppTheme.boxSize.level8)
                .clip(CircleShape)
                .constrainAs(image) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                })

        AppIconButton(
            imageVector = Icons.Rounded.CameraEnhance,
            modifier = Modifier
                .size(AppTheme.buttonSize.level6)
                .constrainAs(button) {
                    bottom.linkTo(image.bottom)
                    end.linkTo(image.end)
                },
            onClick = onClick
        )
    }
}

@Composable
fun TextBlock() {
    var text by remember { mutableStateOf(TextFieldValue("")) }
    var isEditing by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = AppTheme.spacing.level2)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = AppTheme.spacing.level1),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                AppIcon(imageVector = Icons.Rounded.Person)
                Spacer(modifier = Modifier.width(AppTheme.spacing.level2))
                Column {
                    TitleTexts.Level4(text = "Name", color = AppTheme.colors.onSurface)
                    if (text.text.isNotEmpty() && !isEditing) {
                        Spacer(modifier = Modifier.height(AppTheme.spacing.level0))
                        BodyTexts.Level2(text = text.text)
                    }
                }
            }
            if (text.text.isNotEmpty() && !isEditing) {
                AppIconButton(imageVector = Icons.Rounded.Edit) {
                    isEditing = true
                }
            }
        }
        Spacer(modifier = Modifier.height(AppTheme.spacing.level1))
        if (text.text.isEmpty() || isEditing) {
            LaunchedEffect(isEditing) {
                if (isEditing) {
                    focusRequester.requestFocus()
                }
            }

            TextField(value = text,
                onValueChange = {
                    text = it
                    isEditing = it.text.isNotEmpty()
                },
                placeholder = {
                    CaptionTexts.Level4(text = "Enter your full name")
                },
                keyboardActions = KeyboardActions(onDone = {
                    isEditing = false
                }),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester)
                    .onFocusChanged {
                        if (it.isFocused) {
                            isEditing = true
                        }
                    })
        }
    }
}

@Composable
fun EmailBlock() {

    val userEmail by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = AppTheme.spacing.level2)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = AppTheme.spacing.level1),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                AppIcon(imageVector = Icons.Rounded.Email)
                Spacer(modifier = Modifier.width(AppTheme.spacing.level2))
                Column {
                    TitleTexts.Level4(
                        text = "Link your Google Account", color = AppTheme.colors.onSurface
                    )
                    if (userEmail.text.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(AppTheme.spacing.level0))
                        BodyTexts.Level2(text = userEmail.text)
                    }
                }
            }
            AppIconButton(
                imageVector = Icons.Rounded.Link,
                iconTint = AppTheme.colors.primary,
                onClick = {})
        }
    }
}

@Composable
fun PhoneBlock() {
    var text by remember { mutableStateOf(TextFieldValue("")) }
    var phone by remember { mutableStateOf("") }
    var isEditing by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = AppTheme.spacing.level2)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = AppTheme.spacing.level1),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                AppIcon(imageVector = Icons.Rounded.Phone)
                Spacer(modifier = Modifier.width(AppTheme.spacing.level2))
                Column {
                    TitleTexts.Level4(text = "Phone", color = AppTheme.colors.onSurface)
                    if (text.text.isNotEmpty() && !isEditing) {
                        Spacer(modifier = Modifier.height(AppTheme.spacing.level0))
                        BodyTexts.Level2(text = text.text)
                    }
                }
            }
            if (text.text.isNotEmpty() && !isEditing) {
                AppIconButton(imageVector = Icons.Rounded.Edit) {
                    isEditing = true
                }
            }
        }
        Spacer(modifier = Modifier.height(AppTheme.spacing.level1))
        if (text.text.isEmpty() || isEditing) {
            LaunchedEffect(isEditing) {
                if (isEditing) {
                    focusRequester.requestFocus()
                }
            }

            Row(Modifier.fillMaxWidth()) {
                TextField(
                    value = phone,
                    onValueChange = {
                        phone = it
//                        isEditing = it.isNotEmpty()
                    },
                    placeholder = {
                        CaptionTexts.Level4(text = "+91")
                    },
                    keyboardActions = KeyboardActions(onDone = {
//                        isEditing = false
                    }),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done, keyboardType = KeyboardType.Number
                    ),
                    modifier = Modifier
                        .fillMaxWidth(0.2f)
                        .focusRequester(focusRequester)
                        .onFocusChanged {
                            if (it.isFocused) {
                                isEditing = true
                            }
                        },
                )
                Spacer(modifier = Modifier.width(AppTheme.spacing.level0))
                TextField(value = text,
                    onValueChange = {
                        text = it
                        isEditing = it.text.isNotEmpty()
                    },
                    placeholder = {
                        CaptionTexts.Level4(text = "Enter your phone number")
                    },
                    keyboardActions = KeyboardActions(onDone = {
                        isEditing = false
                    }),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done, keyboardType = KeyboardType.Number
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester)
                        .onFocusChanged {
                            if (it.isFocused) {
                                isEditing = true
                            }
                        })
            }
        } else {
            VerifyButton(textToShow = "Verify your Phone")
        }
    }
}

@Composable
fun ReferBlock() {

    var text by remember { mutableStateOf(TextFieldValue("")) }
    var isEditing by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    Column(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = AppTheme.spacing.level2)
    ) {
        ClickableText(
            text = AnnotatedString(text = "Have you got any Referral Code?"),
            onClick = { isEditing = true },
            style = TextStyle.Default.copy(color = AppTheme.colors.primary)
        )
        if (isEditing) {
            Spacer(modifier = Modifier.height(AppTheme.spacing.level1))
            TextField(value = text,
                onValueChange = {
                    text = it
                    isEditing = it.text.isNotEmpty()
                },
                placeholder = {
                    CaptionTexts.Level4(text = "Enter your Referral Code")
                },
                keyboardActions = KeyboardActions(onDone = {
                    isEditing = false
                }),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester)
                    .onFocusChanged {
                        if (it.isFocused) {
                            isEditing = true
                        }
                    })
            Spacer(modifier = Modifier.height(AppTheme.spacing.level2))
            VerifyButton(textToShow = "Apply Referral Code")
        }
    }
}

@Composable
fun GenerateButton(
    modifier: Modifier = Modifier,
    textToShow: String = "",
    onClick: () -> Unit = {},
) {
    AppFilledButton(textToShow = textToShow, modifier = modifier, onClick = onClick)
}

@Composable
fun VerifyButton(textToShow: String, onClick: () -> Unit = {}) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
        AppOutlinedButton(
            textToShow = textToShow,
            onClick = onClick,
            colors = ButtonDefaults.outlinedButtonColors(contentColor = AppTheme.colors.primary),
            border = BorderStroke(color = AppTheme.colors.primary, width = 2.dp)
        )
    }
}

@Preview
@Composable
fun OTPBlock() {

    var otpValue: String by remember { mutableStateOf("") }

    val defaultConfig = OTPCellConfiguration.withDefaults()

    Row(
        Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
    ) {
        OTPInput(
            value = otpValue,
            onValueChange = { newValue, isValid ->
                otpValue = newValue
                if (isValid) {
                    // Validate the value here...
                }
            },
            /* when the value is 1111, all cells will use errorCellConfig */
            isValueInvalid = otpValue == "111111",
            configurations = OTPConfigurations.withDefaults(
                cellsCount = 6,
                emptyCellConfig = defaultConfig,
                filledCellConfig = defaultConfig,
                activeCellConfig = defaultConfig.copy(
                    borderColor = AppTheme.colors.onSurface, borderWidth = AppTheme.elevation.level1
                ),
                errorCellConfig = defaultConfig.copy(
                    borderColor = AppTheme.colors.error, borderWidth = AppTheme.elevation.level1
                ),
                placeHolder = "*",
                cellModifier = Modifier
                    .padding(horizontal = AppTheme.spacing.level0)
                    .size(AppTheme.customSize.level6),
            ),
        )
    }
}