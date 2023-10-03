package fit.asta.health.designsystem.molecular.previews

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.image.AppConstImages
import fit.asta.health.designsystem.molecular.image.AppLocalImage
import fit.asta.health.designsystem.molecular.image.AppNetworkImage


// Preview Function
@Preview("Light Button")
@Preview(
    name = "Dark Button",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
private fun DefaultPreview1() {
    AppTheme {
        Surface {
            AppImageScreen()
        }
    }
}


@Composable
fun AppImageScreen() {
    AppTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(AppTheme.spacing.medium)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.medium)
            ) {
                AppLocalImage(
                    painter = painterResource(id = AppConstImages.errorImg)
                )
                AppNetworkImage(
                    model = "data:image/jpeg;base64,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"
                )
            }
        }
    }
}