package fit.asta.health.tools.exercise.view.level

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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import fit.asta.health.designsystem.components.generic.AppScaffold
import fit.asta.health.designsystem.components.generic.AppTopBarWithHelp
import fit.asta.health.designsystem.molecular.background.AppSurface
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.TitleTexts

@Composable
fun ExerciseLevelScreen(
    onClick: (String) -> Unit,
    onBack: () -> Unit,
    itemList:List<String>
) {

    val itemSelection = remember {
        mutableIntStateOf(-1)
    }
    AppScaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppTopBarWithHelp(
                title = "Level",
                onBack = onBack,
                onHelp = { /*TODO*/ }
            )
        }
    ) {
        LazyColumn(modifier = Modifier.padding(it)) {
            item {
                BodyTexts.Level1(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = "Select the Level based on experience"
                )
            }
            items(count = itemList.size) { indexNumber ->
                LevelItem(onClick, itemList, indexNumber, itemSelection)
            }
        }
    }

}

@Composable
fun LevelItem(
    onClick: (String) -> Unit,
    itemList: List<String>,
    indexNumber: Int,
    itemSelection: MutableState<Int>
) {
    AppSurface(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
            .fillMaxWidth()
            .height(60.dp)
            .clickable {
                onClick(itemList[indexNumber])
                itemSelection.value =
                    if (itemSelection.value != indexNumber) indexNumber
                    else -1
            },
        color = if (itemSelection.value != indexNumber) {
            Color(0xFFE9D7F7)
        } else {
            Color(0xFF7415BD)
        }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TitleTexts.Level3(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .weight(0.7f),
                text = itemList[indexNumber]
            )

            AppIcon(
                modifier = Modifier.weight(0.3f),
                imageVector = Icons.Default.AdsClick,
                contentDescription = null
            )
        }
    }
}