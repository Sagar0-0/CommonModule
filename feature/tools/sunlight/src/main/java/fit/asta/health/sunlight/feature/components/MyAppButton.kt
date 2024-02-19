package fit.asta.health.sunlight.feature.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import fit.asta.health.sunlight.feature.WhiteTransparent


@Composable
fun MyAppButton(
    modifier: Modifier = Modifier,
    title: String,
    textStyle: TextStyle = MaterialTheme.typography.body1
        .copy(
            color = Color.White,
            fontWeight = FontWeight.Bold
        ),
    backgroundColor: Color = WhiteTransparent,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier
            .padding(16.dp),
        onClick = onClick,
        shape = RoundedCornerShape(40.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor =
            backgroundColor
        )
    ) {
        Text(
            text = title,
            style = textStyle,
        )
    }
}