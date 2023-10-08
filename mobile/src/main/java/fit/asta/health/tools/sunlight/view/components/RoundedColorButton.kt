package fit.asta.health.tools.sunlight.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import fit.asta.health.designsystem.molecular.image.AppLocalImage
import fit.asta.health.designsystem.molecular.texts.CaptionTexts

@Composable
fun RoundedColorButton(
    color: Color,
    iconResId: Int,
    text: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .height(68.dp)
            .width(165.5.dp)
            .background(
                color = color,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
        ) {

            item {
                val imagePainter = painterResource(iconResId)
                AppLocalImage(
                    painter = imagePainter,
                    contentDescription = null,
                    modifier = Modifier
                        .size(32.dp)
                        .padding(bottom = 8.dp),
                    contentScale = ContentScale.Fit
                )
            }
            item {
                CaptionTexts.Level1(
                    text = text,
                    color = Color.White
                )
            }
        }
    }
}

