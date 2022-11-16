package fit.asta.health.navigation.home.view.component

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fit.asta.health.R

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToolList()
        }
    }
}

data class Tool(
    val id: Int,
)

val tools = listOf(
    Tool(1),
    Tool(2),
    Tool(3),
    Tool(4),
    Tool(5),
    Tool(6),
)

@Composable
fun ToolList() {
    LazyColumn {
        items(tools) {
            ViewAllTools()
        }
    }
}

@Composable
fun ViewAllTools() {

    Card(elevation = 8.dp,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(16.dp))

    {
        Row(modifier = Modifier.padding(16.dp)) {
            Image(painter = painterResource(id = R.drawable.breathingimage),
                contentDescription = " ",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(140.dp, 100.dp)
                    .clip(RoundedCornerShape(8.dp)))
            Column(modifier = Modifier.padding(8.dp)) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(text = stringResource(id = R.string.breathing_tool_title))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        //.align(Alignment.TopEnd)
                        //.size(32.dp),
                        horizontalArrangement = Arrangement.End,
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_uncheck),
                            contentDescription = null,
                        )
                    }
                }

                Text(text = stringResource(id = R.string.tool_subtitle))

            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Surface(Modifier.fillMaxSize()) {
        ToolList()
    }

}
