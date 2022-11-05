package fit.asta.health.tools.water.view.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fit.asta.health.tools.sunlight.view.components.CardSunBurn

@Preview
@Composable
fun WaterHomeScreen() {

    Column(Modifier.fillMaxWidth()) {

        Spacer(modifier = Modifier.height(32.dp))

        CardSunBurn()

        Spacer(modifier = Modifier.height(48.dp))

        Row(Modifier
            .fillMaxWidth()
            .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Icon(painter = painterResource(id = fit.asta.health.R.drawable.information_icon),
                contentDescription = null,
                modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = "Tie it into a routine. Drink a glass of water every time you brush your teeth, eat a meal or use the bathroom.",
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Thin,
                color = Color.Black)
        }
    }

}