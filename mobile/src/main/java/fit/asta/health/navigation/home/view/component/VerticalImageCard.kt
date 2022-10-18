package fit.asta.health.navigation.home.view.component

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.SizeMode
import fit.asta.health.R
import fit.asta.health.navigation.home.model.domain.HealthTool

@Composable
fun VerticalImageCards(toolsList: List<HealthTool>) {
    val domainName = stringResource(id = R.string.media_url)

    FlowRow(mainAxisSpacing = 8.dp,
        crossAxisSpacing = 16.dp,
        modifier = Modifier.padding(vertical = 16.dp),
        mainAxisSize = SizeMode.Wrap) {

        toolsList.forEach {
            val imgURL = "$domainName${it.url}"
            ToolsCardLayout(imgUrl = imgURL, cardTitle = it.title)
        }
    }

//    LazyVerticalGrid(columns = GridCells.Fixed(2),
//        state = LazyGridState(),
//        modifier = Modifier.height(850.dp),
//        userScrollEnabled = false) {
//        items(toolsList) {
//            val imgURl = "$domainName${it.url}"
//            ToolsCardLayout(cardTitle = it.title, imgUrl = imgURl)
//        }
//    }
}