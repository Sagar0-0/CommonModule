package fit.asta.health.tools.sunlight.view.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fit.asta.health.tools.sunlight.viewmodel.SunlightViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi


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

    Button(onClick = { /*TODO*/ },
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = color)) {
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


@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun ItemRow(viewModel: SunlightViewModel) {
    var selectedId = ""

}