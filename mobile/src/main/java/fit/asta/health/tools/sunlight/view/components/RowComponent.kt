package fit.asta.health.tools.sunlight.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun RowComponent(
    modifier: Modifier = Modifier,
    color: Color,
    title: String,
    titleFontSize: Int,
    titleFontWeight: FontWeight,
    titleLineHeightStyle: Float,
    titleColor: Color,
) {
    Row(modifier = modifier
        .fillMaxWidth()
        .padding(16.dp)
        .background(color = color)
        .clip(shape = RoundedCornerShape(8.dp))) {
        Text(text = title,
            fontSize = titleFontSize.sp,
            fontWeight = titleFontWeight,
            lineHeight = titleLineHeightStyle.sp,
            color = titleColor,
            modifier = Modifier.padding(16.dp))
    }
}


@Preview
@Composable
fun Demo() {
    RowComponent(color = Color.LightGray,
        title = "Fair",
        titleFontSize = 14,
        titleFontWeight = FontWeight.Bold,
        titleLineHeightStyle = 19.6f,
        titleColor = Color.White)
}