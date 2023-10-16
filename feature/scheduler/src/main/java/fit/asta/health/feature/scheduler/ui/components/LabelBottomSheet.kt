package fit.asta.health.feature.scheduler.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.button.AppIconButton
import fit.asta.health.designsystem.molecular.texts.TitleTexts


@Composable
fun CustomLabelBottomSheetLayout(
    text: String,
    @StringRes label: Int,
    onNavigateBack: () -> Unit,
    onSave: (String) -> Unit = {},
) {

    var value by remember {
        mutableStateOf("")
    }
    Column(
        Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            AppIconButton(imageVector = Icons.Default.Close, onClick = onNavigateBack)
            TitleTexts.Level2(
                text = text,
                color = AppTheme.colors.onTertiaryContainer,
                textAlign = TextAlign.Center
            )
            AppIconButton(imageVector = Icons.Default.Check) {
                onSave(value)
            }
        }
    }
    Spacer(modifier = Modifier.height(20.dp))

    CustomTagTextField(
        label = label,
        onValueChange = { value = it })
}
