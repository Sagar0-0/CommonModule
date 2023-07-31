@file:OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class
)

package fit.asta.health.common.ui.components.generic

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


/**The [AppScaffold] is a composable function in Jetpack Compose, used to create a scaffold layout
 *  for the app. It provides a basic structure for apps that typically include a top app bar,
 *  a bottom app bar, and a floating action button. It also allows displaying a Snackbar at the
 *  bottom of the screen.
 * @param modifier the [Modifier] to be applied to this scaffold
 * @param topBar top app bar of the screen
 * @param bottomBar bottom bar of the screen, typically a [NavigationBar]
 * @param snackBarHostState component to host [Snackbar]s that are pushed to be shown via
 * [SnackbarHostState.showSnackbar], typically a [SnackbarHost]
 * @param floatingActionButton Main action button of the screen, typically a [FloatingActionButton]
 * @param content content of the screen. The lambda receives a [PaddingValues] that should be
 * applied to the content root.
 * */


@Composable
fun AppScaffold(
    modifier: Modifier = Modifier,
    snackBarHostState: SnackbarHostState? = null,
    topBar: @Composable (() -> Unit)? = null,
    bottomBar: @Composable (() -> Unit)? = null,
    floatingActionButton: @Composable (() -> Unit)? = null,
    content: @Composable (PaddingValues) -> Unit = {},
) {

    Scaffold(modifier = modifier.fillMaxSize(), topBar = {
        topBar?.let { topBar -> topBar() }
    }, bottomBar = {
        bottomBar?.let { bottomBar ->
            bottomBar()
        }
    }, content = { innerPadding ->
        content(innerPadding)
    }, snackbarHost = {
        snackBarHostState?.let { snackBarHostState ->
            SnackbarHost(hostState = snackBarHostState)
        }
    }, floatingActionButton = {
        floatingActionButton?.let { floatingActionButton ->
            floatingActionButton()
        }
    })
}


/** The [AppBottomSheetScaffold] is a custom composable function that provides a bottom sheet
 *  implementation for app. It is built on top of the standard BottomSheetScaffold
 *  composable but adds some default styling and configuration options for convenience.
 * @param sheetContent the content of the bottom sheet
 * @param modifier the [Modifier] to be applied to this scaffold
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
    modifier: Modifier = Modifier,
    scaffoldState: BottomSheetScaffoldState,
    sheetPeekHeight: Dp = BottomSheetDefaults.SheetPeekHeight,
    sheetShape: Shape = RoundedCornerShape(16.dp),
    sheetShadowElevation: Dp = BottomSheetDefaults.Elevation,
    sheetSwipeEnabled: Boolean = true,
    sheetContent: @Composable ColumnScope.() -> Unit,
    sheetDragHandle: @Composable (() -> Unit)? = { BottomSheetDefaults.DragHandle() },
    topBar: @Composable (() -> Unit)? = null,
    content: @Composable (PaddingValues) -> Unit,
) {

    BottomSheetScaffold(
        sheetContent = sheetContent,
        modifier = modifier,
        scaffoldState = scaffoldState,
        sheetPeekHeight = sheetPeekHeight,
        sheetShape = sheetShape,
        sheetShadowElevation = sheetShadowElevation,
        sheetDragHandle = sheetDragHandle,
        sheetSwipeEnabled = sheetSwipeEnabled,
        topBar = topBar,
        content = content
    )
}