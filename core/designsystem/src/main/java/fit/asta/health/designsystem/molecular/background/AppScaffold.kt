@file:OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class
)

package fit.asta.health.designsystem.molecular.background

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.atomic.AppLoadingScreen

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
    sheetContent: @Composable ColumnScope.() -> Unit,
    modifier: Modifier = Modifier,
    scaffoldState: BottomSheetScaffoldState,
    sheetPeekHeight: Dp = AppTheme.customSize.level7,
    sheetShape: Shape = AppTheme.shape.level2,
    sheetShadowElevation: Dp = AppTheme.elevation.level1,
    sheetContainerColor: Color = AppTheme.colors.surface,
    sheetContentColor: Color = AppTheme.colors.onSurface,
    sheetSwipeEnabled: Boolean = true,
    sheetDragHandle: @Composable (() -> Unit)? = { BottomSheetDefaults.DragHandle() },
    topBar: @Composable (() -> Unit)? = null,
    content: @Composable (PaddingValues) -> Unit,
) {
    BottomSheetScaffold(
        sheetContent = sheetContent,
        modifier = modifier
            .imePadding()
            .navigationBarsPadding(),
        scaffoldState = scaffoldState,
        sheetPeekHeight = sheetPeekHeight,
        sheetShape = sheetShape,
        sheetShadowElevation = sheetShadowElevation,
        sheetDragHandle = sheetDragHandle,
        sheetSwipeEnabled = sheetSwipeEnabled,
        topBar = topBar,
        content = content,
        sheetContainerColor = sheetContainerColor,
        sheetContentColor = sheetContentColor
    )
}