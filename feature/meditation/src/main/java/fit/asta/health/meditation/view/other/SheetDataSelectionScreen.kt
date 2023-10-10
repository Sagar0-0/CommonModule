package fit.asta.health.meditation.view.other

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdsClick
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import fit.asta.health.designsystem.molecular.background.AppScaffold
import fit.asta.health.designsystem.molecular.background.AppSurface
import fit.asta.health.designsystem.molecular.background.AppTopBarWithHelp
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.meditation.remote.network.NetSheetData
import fit.asta.health.meditation.remote.network.Prc

@Composable
fun SheetDataSelectionScreen(
    prc: Prc,
    list: SnapshotStateList<NetSheetData>,
    onSClick: (Int) -> Unit,
    onMClick: (Int) -> Unit,
    onBack: () -> Unit,
) {
    AppScaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppTopBarWithHelp(
                title = prc.ttl,
                onBack = onBack,
                onHelp = { /*TODO*/ }
            )
        }
    ) {

        if (prc.selectType) {//multiple
            MultipleTypeScreen(
                modifier = Modifier.padding(it),
                list = list,
                onClick = onMClick
            )
        } else { //single
            SingleTypeScreen(
                modifier = Modifier.padding(it),
                list = list,
                onClick = onSClick
            )
        }
    }

}

@Composable
fun SingleTypeScreen(
    modifier: Modifier,
    list: SnapshotStateList<NetSheetData>,
    onClick: (Int) -> Unit
) {
    LazyColumn(modifier = modifier) {
        item {
            BodyTexts.Level1(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = "Select the Goals"
            )
        }
        items(list.size) { i ->
            AppSurface(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
                    .fillMaxWidth()
                    .height(60.dp)
                    .clickable {
                        onClick(i)
                    },
                color = if (!list[i].isSelected) {
                    Color(0xFFE9D7F7)
                } else {
                    Color(0xFF7415BD)
                }
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TitleTexts.Level2(
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .weight(0.5f),
                        text = list[i].name
                    )
                    if (list[i].isSelected) {
                        AppIcon(
                            modifier = Modifier.weight(0.5f),
                            imageVector = Icons.Default.AdsClick,
                            contentDescription = null
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MultipleTypeScreen(
    modifier: Modifier,
    list: SnapshotStateList<NetSheetData>,
    onClick: (Int) -> Unit
) {
    LazyColumn(modifier = modifier) {
        item {
            BodyTexts.Level1(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = "Select the Goals"
            )
        }
        items(list.size) { i ->
            AppSurface(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
                    .fillMaxWidth()
                    .height(60.dp)
                    .clickable {
                        onClick(i)
                    },
                color = if (!list[i].isSelected) {
                    Color(0xFFE9D7F7)
                } else {
                    Color(0xFF7415BD)
                }
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TitleTexts.Level2(
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .weight(0.5f),
                        text = list[i].name
                    )
                    if (list[i].isSelected) {
                        AppIcon(
                            modifier = Modifier.weight(0.5f),
                            imageVector = Icons.Default.AdsClick,
                            contentDescription = null
                        )
                    }
                }
            }
        }
    }
}