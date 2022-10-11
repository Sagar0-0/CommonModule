package fit.asta.health.profile.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fit.asta.health.R

@Preview(showSystemUi = true)
@Composable
fun ProfileTopNavBar() {
    Box(Modifier.fillMaxWidth()) {
        Row(Modifier
            .horizontalScroll(rememberScrollState())
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly) {
            NavBarBox(boxImg = R.drawable.accountcircle, boxType = "Details")
            NavBarBox(boxImg = R.drawable.face, boxType = "Physique")
            NavBarBox(boxImg = R.drawable.favorite, boxType = "Health")
            NavBarBox(boxImg = R.drawable.profilelifestyle, boxType = "LifeStyle")
            NavBarBox(boxImg = R.drawable.diet, boxType = "Diet")
        }
    }
}

@Composable
fun NavBarBox(
    boxImg: Int,
    boxType: String,
) {
    Box {
        Column(Modifier.padding(horizontal = 11.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Image(painter = painterResource(id = boxImg),
                contentDescription = null,
                Modifier.size(24.dp))
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = boxType,
                fontSize = 14.sp,
                letterSpacing = 0.4.sp,
                textAlign = TextAlign.Center)
        }
    }
}


