package fit.asta.health.imageCropperV2.preferences

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fit.asta.health.imageCropperV2.cropper.settings.CropTheme

@Composable
fun CropThemeSelection(
    cropTheme: CropTheme,
    onThemeChange: (CropTheme) -> Unit,
) {

    val cropThemeOptions =
        remember {
            listOf(
                CropTheme.Light.toString(),
                CropTheme.Dark.toString(),
                CropTheme.System.toString()
            )
        }

    var showDialog by remember { mutableStateOf(false) }

    val index = when (cropTheme) {
        CropTheme.Light -> 0
        CropTheme.Dark -> 1
        else -> 2
    }

    Text(
        text = cropThemeOptions[index],
        fontSize = 18.sp,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                showDialog = true
            }
            .padding(8.dp)

    )

    if (showDialog) {
        DialogWithMultipleSelection(
            title = "Theme",
            options = cropThemeOptions,
            value = index,
            onDismiss = { showDialog = false },
            onConfirm = {

                val cropThemeChange = when (it) {
                    0 -> CropTheme.Light
                    1 -> CropTheme.Dark
                    else -> CropTheme.System
                }
                onThemeChange(cropThemeChange)
                showDialog = false
            }
        )
    }

}
