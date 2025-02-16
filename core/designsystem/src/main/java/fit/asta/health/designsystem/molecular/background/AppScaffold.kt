@file:OptIn(ExperimentalMaterial3Api::class)

package fit.asta.health.designsystem.molecular.background

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.atomic.AppLoadingScreen
import fit.asta.health.designsystem.atomic.token.AppSheetValue


/**The [AppScaffold] is a composable function in Jetpack Compose, used to create a scaffold layout
 *  for the app.
 * @param modifier the [Modifier] to be applied to this scaffold
 * @param topBar top app bar of the screen
 * @param bottomBar bottom bar of the screen, typically a [NavigationBar]
 * @param snackBarHostState component to host [Snackbar]s that are pushed to be shown via
 * [SnackbarHostState.showSnackbar], typically a [SnackbarHost]
 * @param floatingActionButton Main action button of the screen, typically a [FloatingActionButton]
 * @param content content of the screen. The lambda receives a [PaddingValues] that should be
 * @param isScreenLoading used to show a loading animation(covering whole screen) using the [AppLoadingScreen]
 * applied to the content root.
 * */
@Composable
fun AppScaffold(
    modifier: Modifier = Modifier,
    isScreenLoading: Boolean = false,
    snackBarHostState: SnackbarHostState? = null,
    topBar: @Composable (() -> Unit)? = null,
    bottomBar: @Composable (() -> Unit)? = null,
    floatingActionButton: @Composable (() -> Unit)? = null,
    content: @Composable (PaddingValues) -> Unit = {},
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            topBar?.let { topBar -> topBar() }
        },
        bottomBar = {
            bottomBar?.let { bottomBar ->
                bottomBar()
            }
        },
        snackbarHost = {
            snackBarHostState?.let { snackBarHostState ->
                SnackbarHost(hostState = snackBarHostState)
            }
        },
        floatingActionButton = {
            floatingActionButton?.let { floatingActionButton ->
                floatingActionButton()
            }
        },
        content = { innerPadding ->
            Box(modifier = modifier.fillMaxSize()) {
                content(innerPadding)
                if (isScreenLoading) AppLoadingScreen()
            }
        }
    )
}


/** The [AppBottomSheetScaffold] is a custom composable function that provides a bottom sheet
 *  implementation for app.
 * @param modifier the [Modifier] to be applied to this scaffold
 * @param sheetContent the content of the bottom sheet
 * @param scaffoldState the state of the bottom sheet scaffold
 * @param sheetPeekHeight the height of the bottom sheet when it is collapsed
 * @param sheetShape the shape of the bottom sheet
 * @param sheetShadowElevation the shadow elevation of the bottom sheet
 * @param sheetDragHandle optional visual marker to pull the scaffold's bottom sheet
 * @param sheetSwipeEnabled whether the sheet swiping is enabled and should react to the user's
 * input
 * @param topBar top app bar of the screen
 * @param content content of the screen. The lambda receives a [PaddingValues] that should be
 * applied to the content root.
 * */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBottomSheetScaffold(
//    sheetContent: @Composable ColumnScope.() -> Unit,
//    modifier: Modifier = Modifier,
//    scaffoldState: BottomSheetScaffoldState,
//    sheetPeekHeight: Dp = AppTheme.customSize.level7,
//    sheetShape: Shape = AppTheme.shape.level2,
//    sheetShadowElevation: Dp = AppTheme.elevation.level1,
//    sheetContainerColor: Color = AppTheme.colors.surface,
//    sheetContentColor: Color = AppTheme.colors.onSurface,
//    sheetSwipeEnabled: Boolean = true,
//    sheetDragHandle: @Composable (() -> Unit)? = { BottomSheetDefaults.DragHandle() },
//    topBar: @Composable (() -> Unit)? = null,
//    content: @Composable (PaddingValues) -> Unit,
    sheetContent: @Composable() (ColumnScope.() -> Unit),
    modifier: Modifier = Modifier,
    scaffoldState: BottomSheetScaffoldState = rememberBottomSheetScaffoldState(),
    sheetPeekHeight: Dp = BottomSheetDefaults.SheetPeekHeight,
    sheetMaxWidth: Dp = BottomSheetDefaults.SheetMaxWidth,
    sheetShape: Shape = BottomSheetDefaults.ExpandedShape,
    sheetContainerColor: Color = BottomSheetDefaults.ContainerColor,
    sheetContentColor: Color = contentColorFor(sheetContainerColor),
    sheetTonalElevation: Dp = BottomSheetDefaults.Elevation,
    sheetShadowElevation: Dp = BottomSheetDefaults.Elevation,
    sheetDragHandle: @Composable() (() -> Unit)? = { BottomSheetDefaults.DragHandle() },
    sheetSwipeEnabled: Boolean = true,
    topBar: @Composable() (() -> Unit)? = null,
    snackbarHost: @Composable (SnackbarHostState) -> Unit = { SnackbarHost(it) },
    containerColor: Color = AppTheme.colors.surface,
    contentColor: Color = contentColorFor(containerColor),
    content: @Composable (PaddingValues) -> Unit
) {
    BottomSheetScaffold(
        sheetContent = sheetContent,
        modifier = modifier
            .fillMaxWidth() //Adjust if needed (e.g., .fillMaxHeight(0.8f))
            .navigationBarsPadding()
            .imePadding(),
        scaffoldState = scaffoldState,
        sheetPeekHeight = sheetPeekHeight,
        sheetShape = sheetShape,
        sheetShadowElevation = sheetShadowElevation,
        sheetDragHandle = sheetDragHandle,
        sheetSwipeEnabled = sheetSwipeEnabled,
        sheetMaxWidth = sheetMaxWidth,
        sheetTonalElevation = sheetTonalElevation,
        snackbarHost = snackbarHost,
        contentColor = contentColor,
        topBar = topBar,
        content = content,
        sheetContainerColor = sheetContainerColor,
        sheetContentColor = sheetContentColor
    )
}


@Composable
fun appRememberBottomSheetScaffoldState(
    bottomSheetState: SheetState = AppSheetState(),
    snackbarHostState: SnackbarHostState = appSnackBarHostState()
): BottomSheetScaffoldState {
    return rememberBottomSheetScaffoldState(
        bottomSheetState = bottomSheetState,
        snackbarHostState
    )
}


object AppSnackBarDuration {
    val Short = SnackbarDuration.Short

    /**
     * Show the Snackbar for a long period of time
     */
    val Long = SnackbarDuration.Long

    /**
     * Show the Snackbar indefinitely until explicitly dismissed or action is clicked
     */
    val Indefinite = SnackbarDuration.Indefinite
}

@Composable
fun appSnackBarHostState()
        : SnackbarHostState {
    return remember { SnackbarHostState() }
}

@Composable
fun AppSheetState(
    skipPartialExpanded: Boolean = false,
    initialValue: SheetValue = AppSheetValue.Hidden,
    density: Density = LocalDensity.current,
    confirmValueChange: (SheetValue) -> Boolean = {
        true
    },
    skipHiddenState: Boolean = false
): SheetState {

    return SheetState(
        skipPartiallyExpanded = skipPartialExpanded,
        density = density,
        initialValue = initialValue,
        confirmValueChange = confirmValueChange,
        skipHiddenState = skipHiddenState
    )
}

@Composable
fun appRememberStandardBottomSheetState(): SheetState {
    return rememberStandardBottomSheetState()
}

//use case

@OptIn(
    ExperimentalMaterial3Api::class
)
@Composable
fun MyUse() {
//    val sheetState = appRememberBottomSheetScaffoldState(bottomSheetState = AppSheetState(
//        initialValue = initialValue,
//        skipPartialExpanded = true,
//        skipHiddenState = false,
//        confirmValueChange = confirmValueChange
//    ))
//pass the all param you want here
}
