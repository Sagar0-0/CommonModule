package fit.asta.health.feedback.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import fit.asta.health.common.ui.theme.spacing

@Composable
fun SubmitButton(
    text: String,
    onClick: (() -> Unit)? = null,
) {
    val enabled = remember {
        mutableStateOf(true)
    }
    onClick?.let {
        Button(
            enabled = enabled.value,
            onClick = {
                enabled.value = false
                it()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = spacing.medium),
            shape = RoundedCornerShape(spacing.small),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge,
                color = Color.White,
            )
        }
    }
}