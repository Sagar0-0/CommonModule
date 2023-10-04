package fit.asta.health.feature.profile.basic.ui

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Preview
@OptIn(
    ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class
)
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

    val scope = rememberCoroutineScope()

    var modalBottomSheetValue by remember {
        mutableStateOf(ModalBottomSheetValue.Hidden)
    }
    val modalBottomSheetState =
        androidx.compose.material.rememberModalBottomSheetState(initialValue = modalBottomSheetValue)
    val softwareKeyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    val isDone = remember { mutableStateOf(false) }

    val openSheet = {
        scope.launch {
            modalBottomSheetState.show()
            if (modalBottomSheetValue == ModalBottomSheetValue.HalfExpanded) {
                modalBottomSheetValue = ModalBottomSheetValue.Expanded
            }
        }
    }

    val closeSheet = {
        scope.launch { modalBottomSheetState.hide() }
    }


//    AppTheme {
//        Surface(modifier = Modifier.fillMaxSize()) {
//            AppScaffold(topBar = {
//                AppTopBar(
//                    backIcon = null, title = "Create a Profile"
//                )
//            }) { pad ->
////                Column(
////                    modifier = Modifier
////                        .padding(pad)
////                        .verticalScroll(state = rememberScrollState())
////                        .fillMaxWidth(),
////                    horizontalAlignment = Alignment.CenterHorizontally,
////                    verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.medium)
////                ) {
////                    Surface(
////                        modifier = Modifier
////                            .clip(CircleShape)
////                            .size(AppTheme.boxSize.medium)
////                    ) {
////                        AppLocalImage(
////                            painter = painterResource(id = R.drawable.ic_person),
////                            contentDescription = "Profile",
////                        )
////                        Box(contentAlignment = Alignment.BottomEnd) {
////                            AppIconButton(
////                                imageVector = Icons.Filled.CameraEnhance,
////                                iconTint = AppTheme.colors.onPrimaryContainer
////                            ) {
////                                imagePickerLauncher.launch("image/*")
////                            }
////                        }
////                    }
////
////                    Surface(onClick = { openSheet() }, color = Color.Transparent) {
////                        Row(
////                            modifier = Modifier
////                                .fillMaxWidth()
////                                .padding(start = AppTheme.spacing.medium),
////                            verticalAlignment = Alignment.CenterVertically,
////                        ) {
////                            AppDefaultIcon(
////                                imageVector = Icons.Default.Person,
////                                contentDescription = "User Name Icon"
////                            )
////                            Spacer(modifier = Modifier.width(AppTheme.spacing.extraMedium))
////                            Column(modifier = Modifier.fillMaxWidth()) {
////                                // User icon and name section
////                                Row(
////                                    verticalAlignment = Alignment.CenterVertically,
////                                    horizontalArrangement = Arrangement.SpaceBetween,
////                                    modifier = Modifier
////                                        .fillMaxWidth()
////                                        .padding(end = AppTheme.spacing.medium)
////                                ) {
////                                    Column {
////                                        CaptionTexts.Level4(text = "Name")
////                                        Spacer(modifier = Modifier.height(AppTheme.spacing.extraSmall))
////                                        TitleTexts.Level1(text = "Name")
////                                    }
////                                    AppDefaultIcon(
////                                        imageVector = Icons.Default.Edit,
////                                        contentDescription = "Edit Profile Name"
////                                    )
////                                }
////                                Spacer(modifier = Modifier.height(AppTheme.spacing.small))
////                                AppDivider(
////                                    modifier = Modifier.fillMaxWidth(),
////                                    thickness = AppTheme.spacing.minSmall
////                                )
////                            }
////                        }
////                    }
////                }
//                ModalBottomSheetLayout(sheetState = modalBottomSheetState, sheetContent = {
//                    Column(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(16.dp)
//                    ) {
//                        Text("Bottom Sheet Content")
//                        Spacer(modifier = Modifier.height(16.dp))
//                        TextFieldWithFocus(focusRequester = focusRequester, onDone = {
//                            isDone.value = true
//                        })
//                    }
//                }, content = {
//                    Column(
//                        modifier = Modifier
//                            .padding(pad)
//                            .verticalScroll(state = rememberScrollState())
//                            .fillMaxWidth(),
//                        horizontalAlignment = Alignment.CenterHorizontally,
//                        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.medium)
//                    ) {
//                        Surface(
//                            modifier = Modifier
//                                .clip(CircleShape)
//                                .size(AppTheme.boxSize.medium)
//                        ) {
//                            AppLocalImage(
//                                painter = painterResource(id = R.drawable.ic_person),
//                                contentDescription = "Profile",
//                            )
//                            Box(contentAlignment = Alignment.BottomEnd) {
//                                AppIconButton(
//                                    imageVector = Icons.Filled.CameraEnhance,
//                                    iconTint = AppTheme.colors.onPrimaryContainer
//                                ) {
//                                    imagePickerLauncher.launch("image/*")
//                                }
//                            }
//                        }
//
//                        Surface(onClick = { openSheet() }, color = Color.Transparent) {
//                            Row(
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .padding(start = AppTheme.spacing.medium),
//                                verticalAlignment = Alignment.CenterVertically,
//                            ) {
//                                AppDefaultIcon(
//                                    imageVector = Icons.Default.Person,
//                                    contentDescription = "User Name Icon"
//                                )
//                                Spacer(modifier = Modifier.width(AppTheme.spacing.extraMedium))
//                                Column(modifier = Modifier.fillMaxWidth()) {
//                                    // User icon and name section
//                                    Row(
//                                        verticalAlignment = Alignment.CenterVertically,
//                                        horizontalArrangement = Arrangement.SpaceBetween,
//                                        modifier = Modifier
//                                            .fillMaxWidth()
//                                            .padding(end = AppTheme.spacing.medium)
//                                    ) {
//                                        Column {
//                                            CaptionTexts.Level4(text = "Name")
//                                            Spacer(modifier = Modifier.height(AppTheme.spacing.extraSmall))
//                                            TitleTexts.Level1(text = "Name")
//                                        }
//                                        AppDefaultIcon(
//                                            imageVector = Icons.Default.Edit,
//                                            contentDescription = "Edit Profile Name"
//                                        )
//                                    }
//                                    Spacer(modifier = Modifier.height(AppTheme.spacing.small))
//                                    AppDivider(
//                                        modifier = Modifier.fillMaxWidth(),
//                                        thickness = AppTheme.spacing.minSmall
//                                    )
//                                }
//                            }
//                        }
//                    }
//                })
//            }
//        }
//    }

//    ModalBottomSheetLayout(sheetState = modalBottomSheetState, sheetContent = {
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp)
//        ) {
//            Text("Bottom Sheet Content")
//            Spacer(modifier = Modifier.height(16.dp))
//            TextFieldWithFocus(focusRequester = focusRequester, onDone = {
//                isDone.value = true
//            })
//        }
//    }, content = {
//        Column(
//            modifier = Modifier
//                .padding(pad)
//                .verticalScroll(state = rememberScrollState())
//                .fillMaxWidth(),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.medium)
//        ) {
//            Surface(
//                modifier = Modifier
//                    .clip(CircleShape)
//                    .size(AppTheme.boxSize.medium)
//            ) {
//                AppLocalImage(
//                    painter = painterResource(id = R.drawable.ic_person),
//                    contentDescription = "Profile",
//                )
//                Box(contentAlignment = Alignment.BottomEnd) {
//                    AppIconButton(
//                        imageVector = Icons.Filled.CameraEnhance,
//                        iconTint = AppTheme.colors.onPrimaryContainer
//                    ) {
//                        imagePickerLauncher.launch("image/*")
//                    }
//                }
//            }
//
//            Surface(onClick = { openSheet() }, color = Color.Transparent) {
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(start = AppTheme.spacing.medium),
//                    verticalAlignment = Alignment.CenterVertically,
//                ) {
//                    AppDefaultIcon(
//                        imageVector = Icons.Default.Person,
//                        contentDescription = "User Name Icon"
//                    )
//                    Spacer(modifier = Modifier.width(AppTheme.spacing.extraMedium))
//                    Column(modifier = Modifier.fillMaxWidth()) {
//                        // User icon and name section
//                        Row(
//                            verticalAlignment = Alignment.CenterVertically,
//                            horizontalArrangement = Arrangement.SpaceBetween,
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(end = AppTheme.spacing.medium)
//                        ) {
//                            Column {
//                                CaptionTexts.Level4(text = "Name")
//                                Spacer(modifier = Modifier.height(AppTheme.spacing.extraSmall))
//                                TitleTexts.Level1(text = "Name")
//                            }
//                            AppDefaultIcon(
//                                imageVector = Icons.Default.Edit,
//                                contentDescription = "Edit Profile Name"
//                            )
//                        }
//                        Spacer(modifier = Modifier.height(AppTheme.spacing.small))
//                        AppDivider(
//                            modifier = Modifier.fillMaxWidth(),
//                            thickness = AppTheme.spacing.minSmall
//                        )
//                    }
//                }
//            }
//        }
//    })

    LaunchedEffect(modalBottomSheetState.isVisible) {
        if (modalBottomSheetState.isVisible) {
            // Focus the TextField when the BottomSheet is expanded
            focusRequester.requestFocus()
            // Show the keyboard
            softwareKeyboardController?.show()
        }
        if (!modalBottomSheetState.isVisible) {
            softwareKeyboardController?.hide()
            isDone.value = false
        }
    }

    LaunchedEffect(key1 = isDone.value, block = {
        if (isDone.value) {
            softwareKeyboardController?.hide()
            delay(1000)
            closeSheet()
        }
    })
}


@Preview
@OptIn(
    ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class
)
@Composable
fun BottomSheetWithTextField() {

    val scope = rememberCoroutineScope()

    var modalBottomSheetValue by remember {
        mutableStateOf(ModalBottomSheetValue.Hidden)
    }

    val modalBottomSheetState =
        androidx.compose.material.rememberModalBottomSheetState(initialValue = modalBottomSheetValue)

    val softwareKeyboardController = LocalSoftwareKeyboardController.current

    val focusRequester = remember { FocusRequester() }

    val isDone = remember { mutableStateOf(false) }

    val openSheet = {
        scope.launch {
            modalBottomSheetState.show()
            if (modalBottomSheetValue == ModalBottomSheetValue.HalfExpanded) {
                modalBottomSheetValue = ModalBottomSheetValue.Expanded
            }
        }
    }

    val closeSheet = {
        scope.launch { modalBottomSheetState.hide() }
    }

//    AppModalBottomSheetLayout(sheetState = modalBottomSheetState, sheetContent = {
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp)
//        ) {
//            Text("Bottom Sheet Content")
//            Spacer(modifier = Modifier.height(16.dp))
//            TextFieldWithFocus(focusRequester = focusRequester, onDone = {
//                isDone.value = true
//            })
//        }
//    }) {
//        Button(onClick = { openSheet() }) {
//            Text("Show Bottom Sheet")
//        }
//    }

    BottomSheetScaffold(sheetContent = {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("Bottom Sheet Content")
            Spacer(modifier = Modifier.height(16.dp))
            TextFieldWithFocus(focusRequester = focusRequester, onDone = {
                isDone.value = true
            })
        }
    }, topBar = { TopAppBar(title = { Text(text = "Basic Profile Screen") }) }) { p ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(p), contentAlignment = Alignment.Center
        ) {
            Button(onClick = { openSheet() }) {
                Text("Show Bottom Sheet")
            }
        }
    }

    LaunchedEffect(modalBottomSheetState.isVisible) {
        if (modalBottomSheetState.isVisible) {
            // Focus the TextField when the BottomSheet is expanded
            focusRequester.requestFocus()
            // Show the keyboard
            softwareKeyboardController?.show()
        }
        if (!modalBottomSheetState.isVisible) {
            softwareKeyboardController?.hide()
            isDone.value = false
        }
    }

    LaunchedEffect(key1 = isDone.value, block = {
        if (isDone.value) {
            softwareKeyboardController?.hide()
            delay(1000)
            closeSheet()
        }
    })


}

@Composable
fun TextFieldWithFocus(
    focusRequester: FocusRequester,
    onDone: (KeyboardActionScope.() -> Unit)?,
) {
    val imeAction = remember { KeyboardActions(onDone = onDone) }
    val keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done)

    TextField(value = "",
        onValueChange = { /* TODO: Handle text change */ },
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester),
        keyboardOptions = keyboardOptions,
        keyboardActions = imeAction,
        label = { Text("Enter text") })
}


