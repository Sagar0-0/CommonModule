package fit.asta.health.navigation.today.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fit.asta.health.R

@Composable
fun NameAndMoodHomeScreenHeader() {

    val poppinsFontFamily = FontFamily(Font(R.font.poppins_medium, FontWeight.Medium))

    val interFontFamily = FontFamily(Font(R.font.inter_regular, FontWeight.Normal))

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(59.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.size(153.dp, 36.dp)) {
                Text(
                    text = stringResource(id = R.string.hello_Aastha),
                    fontSize = 24.sp,
                    fontFamily = poppinsFontFamily,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            Box {
                Text(text = "\uD83D\uDC4B", fontSize = 24.sp, fontFamily = poppinsFontFamily)
            }
        }
        Box(modifier = Modifier.size(147.dp, 15.dp)) {
            Text(
                text = stringResource(id = R.string.greeting),
                fontSize = 12.sp,
                fontFamily = interFontFamily,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }

}
