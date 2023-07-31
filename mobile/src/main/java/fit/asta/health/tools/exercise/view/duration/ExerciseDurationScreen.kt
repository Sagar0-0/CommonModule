package fit.asta.health.tools.exercise.view.duration

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fit.asta.health.common.ui.components.generic.AppScaffold
import fit.asta.health.common.ui.components.generic.AppTopBarWithHelp
import fit.asta.health.tools.exercise.view.level.LevelItem

@Composable
fun ExerciseDurationScreen(
    onClick: (String) -> Unit,
    onBack: () -> Unit,
    itemList: List<String>
) {


    val itemSelection = remember {
        mutableIntStateOf(-1)
    }
    AppScaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppTopBarWithHelp(
                title = "Duration",
                onBack = onBack,
                onHelp = { /*TODO*/ }
            )
        }
    ) {
        LazyColumn(modifier = Modifier.padding(it)) {
            item {
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = "Select the Level based on experience",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            items(count = itemList.size) { indexNumber ->
                LevelItem(onClick, itemList, indexNumber, itemSelection)
            }
        }
    }

}