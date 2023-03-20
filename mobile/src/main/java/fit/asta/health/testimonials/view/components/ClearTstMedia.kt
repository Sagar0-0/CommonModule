package fit.asta.health.testimonials.view.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import fit.asta.health.R

@Composable
fun ClearTstMedia(onTstMediaClear: () -> Unit) {
    Box(contentAlignment = Alignment.TopEnd, modifier = Modifier.fillMaxWidth(1f)) {
        IconButton(onClick = {
            onTstMediaClear()
        }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_delete_forever),
                contentDescription = null,
                tint = Color(0xffFF4081)
            )
        }
    }

}