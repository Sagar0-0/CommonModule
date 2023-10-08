package fit.asta.health.tools.breathing.view.goal

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import fit.asta.health.designsystem.molecular.AppScaffold
import fit.asta.health.designsystem.components.generic.AppTopBarWithHelp
import fit.asta.health.designsystem.molecular.background.AppSurface
import fit.asta.health.designsystem.molecular.icon.AppIcon
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.tools.walking.model.ListItem

@Composable
fun GoalsScreen(
    onClick: (List<String>) -> Unit,
    onBack: () -> Unit
) {
    val goals  = listOf("De-Stress", "Fall Asleep", "Anxiety", "Clear your Mind")
    var items by remember {
        mutableStateOf(goals.map {
            ListItem(title = it, isSelected = false)
        })
    }
    AppScaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppTopBarWithHelp(
                title = "Goals",
                onBack = onBack,
                onHelp = { /*TODO*/ }
            )
        }
    ) {
        LazyColumn(modifier = Modifier.padding(it)) {
            item {
                BodyTexts.Level1(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = "Select goals for your breathing exercise"
                )
            }
            items(count = items.size) { indexNumber ->
                AppSurface(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
                        .fillMaxWidth()
                        .height(60.dp)
                        .clickable {
                            items = items.mapIndexed { j, item ->
                                if (indexNumber == j) {
                                    item.copy(isSelected = !item.isSelected)
                                } else item
                            }
                            onClick(
                                items
                                    .filter { it.isSelected }
                                    .map { it.title })
                        },
                    color =if (!items[indexNumber].isSelected) {
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
                        BodyTexts.Level3(
                            modifier = Modifier
                                .padding(start = 16.dp)
                                .weight(0.5f),
                            text = items[indexNumber].title
                        )

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