package fit.asta.health.navigation.home.view.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp

@Composable
fun MyToolsAndViewAll(
    myTools: String,
    allTools: String,
    onClick: (value: Int) -> Unit,
) {
    Row(horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp, horizontal = 16.dp)) {
        Text(text = myTools, style = MaterialTheme.typography.h6, color = Color.Black)
        Box(modifier = Modifier.clickable(enabled = true, onClick = ({}))) {
            ClickableText(text = AnnotatedString(allTools),
                style = MaterialTheme.typography.h6,
                onClick = onClick)
        }
    }
}