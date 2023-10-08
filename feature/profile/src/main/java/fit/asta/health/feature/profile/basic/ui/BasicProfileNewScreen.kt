package fit.asta.health.feature.profile.basic.ui

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraEnhance
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.components.generic.AppDefaultIcon
import fit.asta.health.designsystem.components.generic.AppModalBottomSheetLayout
import fit.asta.health.designsystem.molecular.AppScaffold
import fit.asta.health.designsystem.components.generic.AppTextField
import fit.asta.health.designsystem.components.generic.AppTopBar
import fit.asta.health.designsystem.molecular.animations.AppDivider
import fit.asta.health.designsystem.molecular.button.AppIconButton
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.image.AppLocalImage
import fit.asta.health.designsystem.molecular.texts.CaptionTexts
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.resources.drawables.R
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
    val modalBottomSheetState = rememberModalBottomSheetState(initialValue = modalBottomSheetValue)
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

    ModalBottomSheetLayout(sheetState = modalBottomSheetState, sheetContent = {
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
    }, content = {
        Column(
            modifier = Modifier
                .verticalScroll(state = rememberScrollState())
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level3)
        ) {
            Surface(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(AppTheme.boxSize.level3)
            ) {
                AppLocalImage(
                    painter = painterResource(id = R.drawable.ic_person),
                    contentDescription = "Profile",
                )
                Box(contentAlignment = Alignment.BottomEnd) {
                    AppIconButton(
                        imageVector = Icons.Filled.CameraEnhance,
                        iconTint = AppTheme.colors.onPrimaryContainer
                    ) {
                        imagePickerLauncher.launch("image/*")
                    }
                }
            }

            Surface(onClick = { openSheet() }, color = Color.Transparent) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = AppTheme.spacing.level3),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    AppIcon(
                        imageVector = Icons.Default.Person, contentDescription = "User Name Icon"
                    )
                    Spacer(modifier = Modifier.width(AppTheme.spacing.level2))
                    Column(modifier = Modifier.fillMaxWidth()) {
                        // User icon and name section
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(end = AppTheme.spacing.level2)
                        ) {
                            Column {
                                CaptionTexts.Level4(text = "Name")
                                Spacer(modifier = Modifier.height(AppTheme.spacing.level1))
                                TitleTexts.Level1(text = "Name")
                            }
                            AppIcon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Edit Profile Name"
                            )
                        }
                        Spacer(modifier = Modifier.height(AppTheme.spacing.level1))
                        AppDivider(
                            modifier = Modifier.fillMaxWidth(), thickness = AppTheme.spacing.level1
                        )
                    }
                }
            }
        }
    })

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

    val modalBottomSheetState = rememberModalBottomSheetState(initialValue = modalBottomSheetValue)

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


@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicProfileLayout() {

    AppScaffold(topBar = {
        AppTopBar(title = "Basic Profile Creation") {

        }
    }, content = { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
//            BasicProfileCreateScreen()
            DemoDialog()
        }
    })

}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BasicProfileCreateScreen() {

    val scope = rememberCoroutineScope()

    var modalBottomSheetValue by remember {
        mutableStateOf(ModalBottomSheetValue.Hidden)
    }

    val modalBottomSheetState = rememberModalBottomSheetState(initialValue = modalBottomSheetValue)

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

    AppModalBottomSheetLayout(
        sheetState = modalBottomSheetState,
        sheetContent = { BasicProfileBtmSheetContent() }) {
        BasicProfileContent(onClick = { openSheet() })
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BasicProfileContent(onClick: () -> Unit) {

    CompositionLocalProvider(LocalOverscrollConfiguration provides null) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = AppTheme.spacing.level3)
//            .verticalScroll(rememberScrollState())
                .background(color = AppTheme.colors.background),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(AppTheme.spacing.level3))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = AppTheme.spacing.level3)
                    .clickable { onClick() },
                verticalAlignment = Alignment.CenterVertically,
            ) {
                AppDefaultIcon(
                    imageVector = Icons.Default.Person, contentDescription = "User Name Icon"
                )
                Spacer(modifier = Modifier.width(AppTheme.spacing.level2))
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = AppTheme.spacing.level3)
                    ) {
                        Column {
                            CaptionTexts.Level4(text = "Name")
                            Spacer(modifier = Modifier.height(AppTheme.spacing.level1))
                            TitleTexts.Level1(text = "Name")
                        }
                        AppDefaultIcon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit Profile Name"
                        )
                    }
                    Spacer(modifier = Modifier.height(AppTheme.spacing.level2))
                    AppDivider(
                        modifier = Modifier.fillMaxWidth(), thickness = AppTheme.spacing.level2
                    )
                }
            }
        }
    }
}


@Composable
fun BasicProfileBtmSheetContent() {
    Box(
        Modifier
            .fillMaxSize()
            .background(AppTheme.colors.onSurface)
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(AppTheme.spacing.level3)
        ) {
            TitleTexts.Level3(text = "Enter you name")
            Spacer(modifier = Modifier.height(AppTheme.spacing.level3))
            NameTextField()
        }
    }
}

@Composable
fun NameTextField() {
    var text by remember { mutableStateOf("") }

    AppTextField(value = text, onValueChange = { text = it })
}

@Composable
fun DemoDialog() {

    var openDialog by remember { mutableStateOf(false) }

    var text by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { openDialog = true }) {
            Text(text = "Click Me")
        }
    }


    if (openDialog) {
        Dialog(onDismissRequest = { openDialog = false }) {
            AppCard {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "Enter your name")
                    Spacer(modifier = Modifier.height(16.dp))
                    AppTextField(value = text, onValueChange = { text = it })
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }

}