package fit.asta.health.navigation.home.view.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MyToolsAndViewAll(
    myTools:String,
    allTools:String
) {
    Row(horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp)) {
        Text(text = myTools, style = MaterialTheme.typography.h6, color = Color.Black)
        Box(modifier = Modifier.clickable(enabled = true, onClick = ({}))) {
            Text(text = allTools, style = MaterialTheme.typography.h6, color = Color.Blue)
        }
    }
}