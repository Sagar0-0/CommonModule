package fit.asta.health.common.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Preview
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