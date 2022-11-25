package fit.asta.health.navigation.home.view.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fit.asta.health.R


@Composable
fun NewDemoCard(modifier: Modifier = Modifier, tool: String) {

    Card(modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0x1A959393))) {
        Column(modifier = Modifier.background(Color.Transparent)) {

            Box {

                Image(painter = painterResource(id = R.drawable.weatherimage),
                    contentDescription = null,
                    modifier = Modifier
                        .height(107.dp)
                        .clip(RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)),
                    contentScale = ContentScale.Crop)


                Box(contentAlignment = Alignment.TopStart, modifier = Modifier.fillMaxWidth()) {
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                        horizontalArrangement = Arrangement.End) {
                        Card(modifier = Modifier
                            .size(48.dp)
                            .padding(top = 8.dp, start = 15.dp)
                            .clip(RoundedCornerShape(8.dp))) {
                            IconButton(onClick = { /*TODO*/ }) {
                                Icon(painter = painterResource(id = R.drawable.clockalarm),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(32.dp)
                                        .padding(6.dp))
                            }
                        }
                    }
                }

            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = tool,
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .height(44.dp),
                color = Color(0xDE000000),
                fontSize = 18.sp,
                lineHeight = 28.sp,
                fontWeight = FontWeight.Normal)

        }
    }

}


@Preview
@Composable
fun NewDemo() {

    val items =
        listOf("Water Tool", "Water Tool", "Water Tool", "Water Tool", "Water Tool", "Water Tool")


    LazyVerticalGrid(columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)) {
        itemsIndexed(items = items) { _: Int, tools: String ->
            NewDemoCard(tool = tools)
        }
    }

}