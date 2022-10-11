package fit.asta.health.profile.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import fit.asta.health.R

@Composable
fun AddIcon() {
    Box(contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(24.dp)
            .clip(shape = CircleShape)
            .background(color = Color(0xff0088FF))) {
        Image(painter = painterResource(id = R.drawable.add),
            contentDescription = null,
            modifier = Modifier.size(14.dp))
    }
}