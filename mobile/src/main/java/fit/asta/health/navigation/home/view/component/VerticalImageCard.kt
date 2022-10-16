package fit.asta.health.navigation.home.view.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowRow
import fit.asta.health.R
import fit.asta.health.navigation.home.model.domain.HealthTool


@Composable
fun VerticalImageCards(toolsList: List<HealthTool>) {
    val domainName = stringResource(id = R.string.media_url)

    FlowRow(mainAxisSpacing = 8.dp,
        crossAxisSpacing = 16.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)) {
        for (i in toolsList) {
            val imgUrl = "$domainName${i.url}"
            Column(Modifier.fillMaxWidth()) {
                ToolsCardLayout(cardTitle = i.title, imgUrl = imgUrl)
            }
        }
    }

}