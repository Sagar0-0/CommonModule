package fit.asta.health.feature.profile.basic.ui

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Phone
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
import androidx.constraintlayout.compose.ConstraintLayout
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.background.AppScaffold
import fit.asta.health.designsystem.molecular.background.AppTopBar
import fit.asta.health.designsystem.molecular.button.AppFilledButton
import fit.asta.health.designsystem.molecular.button.AppIconButton
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.image.AppLocalImage
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.CaptionTexts
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.resources.drawables.R

@Preview
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

    AppScaffold(topBar = {
        AppTopBar(title = "Create Basic Profile")
    }, content = { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(
                    rememberScrollState()
                ), verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level3)
        ) {
            CircularImageWithIconButton(onClick = { imagePickerLauncher.launch("image/*") })
            TextBlock()
            EmailBlock()
            PhoneBlock()
            ReferBlock()
            GenerateButton(
                textToShow = "Create your Profile",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppTheme.spacing.level3)
            )
        }
    })
}

@Composable
fun CircularImageWithIconButton(modifier: Modifier = Modifier, onClick: () -> Unit = {}) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = AppTheme.spacing.level3)
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

        AppIconButton(imageVector = Icons.Rounded.CameraEnhance,
            onClick = onClick,
            modifier = Modifier
                .size(AppTheme.buttonSize.level6)
                .constrainAs(button) {
                    bottom.linkTo(image.bottom)
                    end.linkTo(image.end)
                })
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
            .padding(horizontal = AppTheme.spacing.level3)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = AppTheme.spacing.level2),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                AppIcon(imageVector = Icons.Rounded.Person)
                Spacer(modifier = Modifier.width(AppTheme.spacing.level3))
                Column {
                    TitleTexts.Level4(text = "Name", color = AppTheme.colors.onSurface)
                    if (text.text.isNotEmpty() && !isEditing) {
                        Spacer(modifier = Modifier.height(AppTheme.spacing.level1))
                        BodyTexts.Level2(text = text.text)
                    }
                }
            }
            if (text.text.isNotEmpty() && !isEditing) {
                AppIconButton(imageVector = Icons.Rounded.Edit, onClick = {
                    isEditing = true
                })
            }
        }
        Spacer(modifier = Modifier.height(AppTheme.spacing.level2))
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

    var text by remember { mutableStateOf(TextFieldValue("")) }
    var isEditing by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = AppTheme.spacing.level3)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = AppTheme.spacing.level2),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                AppIcon(imageVector = Icons.Rounded.Email)
                Spacer(modifier = Modifier.width(AppTheme.spacing.level3))
                Column {
                    TitleTexts.Level4(text = "Email", color = AppTheme.colors.onSurface)
                    if (text.text.isNotEmpty() && !isEditing) {
                        Spacer(modifier = Modifier.height(AppTheme.spacing.level1))
                        BodyTexts.Level2(text = text.text)
                    }
                }
            }
            if (text.text.isNotEmpty() && !isEditing) {
                AppIconButton(imageVector = Icons.Rounded.Edit, onClick = {
                    isEditing = true
                })
            }
        }
        Spacer(modifier = Modifier.height(AppTheme.spacing.level2))
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
        } else {
            GenerateButton(textToShow = "Verify your email")
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
            .padding(horizontal = AppTheme.spacing.level3)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = AppTheme.spacing.level2),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                AppIcon(imageVector = Icons.Rounded.Phone)
                Spacer(modifier = Modifier.width(AppTheme.spacing.level3))
                Column {
                    TitleTexts.Level4(text = "Phone", color = AppTheme.colors.onSurface)
                    if (text.text.isNotEmpty() && !isEditing) {
                        Spacer(modifier = Modifier.height(AppTheme.spacing.level1))
                        BodyTexts.Level2(text = text.text)
                    }
                }
            }
            if (text.text.isNotEmpty() && !isEditing) {
                AppIconButton(imageVector = Icons.Rounded.Edit, onClick = {
                    isEditing = true
                })
            }
        }
        Spacer(modifier = Modifier.height(AppTheme.spacing.level2))
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
                Spacer(modifier = Modifier.width(AppTheme.spacing.level1))
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
            GenerateButton(textToShow = "Verify your phone")
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
            .padding(horizontal = AppTheme.spacing.level3)
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
            Spacer(modifier = Modifier.height(AppTheme.spacing.level3))
            GenerateButton(textToShow = "Apply Referral Code")
        }
    }
}

@Composable
fun GenerateButton(
    modifier: Modifier = Modifier,
    textToShow: String = "",
    onClick: () -> Unit = {},
) {
    AppFilledButton(textToShow = textToShow, onClick = onClick, modifier = modifier)
}