package fit.asta.health.tools.exercise.view.duration

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fit.asta.health.R
import fit.asta.health.common.ui.components.AppScaffold
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
            NavigationBar(
                content = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        IconButton(onClick = onBack) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_exercise_back),
                                contentDescription = null,
                                Modifier.size(24.dp)
                            )
                        }
                        Text(
                            text = "Duration",
                            fontSize = 20.sp,
                            color = MaterialTheme.colorScheme.onBackground,
                            textAlign = TextAlign.Center
                        )
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_physique),
                                contentDescription = null,
                                Modifier.size(24.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                },
                tonalElevation = 10.dp,
                containerColor = MaterialTheme.colorScheme.onPrimary
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