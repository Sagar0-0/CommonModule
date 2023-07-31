package fit.asta.health.common.ui.components.functional

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BottomSheetButton(
    title: String,
    modifier: Modifier = Modifier,
    colors: ButtonColors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),
    onClick: (() -> Unit)? = null,
) {
    onClick?.let {
        Button(
            onClick = it,
            shape = RoundedCornerShape(5.dp),
            colors = colors,
            modifier = modifier.fillMaxWidth()
        ) {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.onPrimary,
                lineHeight = 16.sp,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}
//Color(0xff43A047)