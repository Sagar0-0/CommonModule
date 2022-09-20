package fit.asta.health.home.homeFragments

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MyToolsAndViewAll() {
    Row(horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp)) {
        Text(text = "MyTools", style = MaterialTheme.typography.h6, color = Color.Black)
        Box(modifier = Modifier.clickable(enabled = true, onClick = ({}))) {
            Text(text = "All Tools", style = MaterialTheme.typography.h6, color = Color.Blue)
        }
    }
}