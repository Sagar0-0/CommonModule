package fit.asta.health.profile.bottomsheets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fit.asta.health.profile.bottomsheets.components.PhotoUploadBottomSheetLayout

@Suppress("UNUSED_EXPRESSION")
@Preview
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PhotoUploadBottomSheet() {

    val state = rememberBottomSheetScaffoldState()

    BottomSheetScaffold(
        sheetContent = {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                PhotoUploadBottomSheetLayout()
            }
        },
        sheetShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        sheetElevation = 10.dp,
        sheetBackgroundColor = MaterialTheme.colorScheme.onPrimary,
        scaffoldState = state,
        drawerBackgroundColor = Color.Green) {
        Text(text = "Hello")
    }
}
