package fit.asta.health.tools.breathing.view.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fit.asta.health.R


@Composable
fun CardBreathingRatio() {
    Card {
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            Text(text = "Nadi Shodana")
            Row(verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()) {
                Column {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(painter = painterResource(id = R.drawable.ic_exercise_back),
                            contentDescription = null)

                    }
                    Text(text = "Reset")
                }
                Column {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(painter = painterResource(id = R.drawable.ic_exercise_back),
                            contentDescription = null)

                    }
                    Text(text = "Ratio")
                }
                Column {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(painter = painterResource(id = R.drawable.ic_exercise_back),
                            contentDescription = null)

                    }
                    Text(text = "Duration")
                }
                Column {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(painter = painterResource(id = R.drawable.ic_exercise_back),
                            contentDescription = null)

                    }
                    Text(text = "Info")
                }

            }
        }
    }
}

@Preview
@Composable
fun ComposablePreviewDemo() {
    CardBreathingRatio()
}
