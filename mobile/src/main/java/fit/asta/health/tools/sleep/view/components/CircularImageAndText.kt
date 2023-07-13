package fit.asta.health.tools.sleep.view.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * This composable function is used to Make a Circular Button Type which will be containing a
 * image and a text below it
 *
 * @param image This is the image we would be drawing in the UI
 * @param text THis is the text we would be drawing in the UI
 * @param onClick This is the function which is executed when the UI element is clicked
 */
@Composable
fun CircularImageAndText(
    @DrawableRes image: Int? = null,
    text: String,
    onClick: (() -> Unit)? = null
) {
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(Color(0xFF594343))
            .size(72.dp)
            .clickable {
                if (onClick != null) {
                    onClick()
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(6.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            image?.let { painterResource(id = it) }?.let {
                Image(
                    painter = it,
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                )
            }
            Text(
                text = text,

                fontStyle = FontStyle.Normal,
                fontSize = 12.sp
            )
        }
    }
}