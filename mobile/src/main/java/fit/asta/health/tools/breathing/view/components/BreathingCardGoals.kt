package fit.asta.health.tools.breathing.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fit.asta.health.R

@Composable
fun CardBreathingGoals() {
    Row {


        Card(
            modifier = Modifier
                .size(121.5.dp)
                .wrapContentHeight()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                Image(painter = painterResource(id = R.drawable.sleepimage),
                    contentDescription = null,
                    contentScale = ContentScale.Fit)
            }
            Column(
                modifier = Modifier
                    .size(121.5.dp)
                    .wrapContentHeight()
                //.padding(16.dp)
            ) {
                Text(text = "De-stress")
                Text(text = "Night Time")
            }
        }

        Card(
            modifier = Modifier
                .size(121.5.dp)
                .wrapContentHeight()

            //Spacer(modifier = Modifier.width(16.dp))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                Image(painter = painterResource(id = R.drawable.sleepimage),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth)
            }
            Column(
                modifier = Modifier
                    .size(121.5.dp)
                    .wrapContentHeight()
                //.padding(16.dp)
            ) {
                Text(text = "De-stress")
                Text(text = "Night Time")
            }
        }
    }
}

@Preview
@Composable
fun ComposablePreview() {
    CardBreathingGoals()
}