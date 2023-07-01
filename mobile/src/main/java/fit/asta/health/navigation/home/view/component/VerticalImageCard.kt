package fit.asta.health.navigation.home.view.component

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import fit.asta.health.R
import fit.asta.health.navigation.home.model.domain.ToolsHomeRes

@Composable
fun VerticalImageCards(toolsList: List<ToolsHomeRes.ToolsHome.HealthTool>) {
    val domainName = stringResource(id = R.string.media_url)

//    FlowRow(mainAxisSpacing = 8.dp,
//        crossAxisSpacing = 16.dp,
//        modifier = Modifier.padding(vertical = 16.dp),
//        mainAxisSize = SizeMode.Wrap) {
//
//        toolsList.forEach {
//            val imgURL = "$domainName${it.url}"
//            ToolsCardLayout(imgUrl = imgURL, cardTitle = it.title)
//        }
//    }

    LazyVerticalGrid(columns = GridCells.Fixed(2),
        state = LazyGridState(),
        modifier = Modifier
            .height(850.dp)
            .padding(16.dp),
        userScrollEnabled = false) {
        items(toolsList) {
            val imgURl = "$domainName${it.url}"
            ToolsCardLayout(cardTitle = it.title, imgUrl = imgURl)
        }
    }
}