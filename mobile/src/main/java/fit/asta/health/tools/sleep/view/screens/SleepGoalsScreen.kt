package fit.asta.health.tools.sleep.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.tools.sleep.view.components.SleepCardItems

@Composable
fun SleepGoalsScreen(
    navController: NavController,
    optionList: List<String>,
    currentSelectedOption: String,
    onClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 24.dp, bottom = 16.dp, start = 16.dp, end = 16.dp),
        verticalArrangement = Arrangement.spacedBy(spacing.medium),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        items(optionList.size) {

            val currentItem = optionList[it]
            if (currentItem == currentSelectedOption) {
                Box(
                    modifier = Modifier
                        .background(Color.Red)
                ) {
                    SleepCardItems(textToShow = currentItem)
                }
            } else
                SleepCardItems(textToShow = currentItem)

        }
    }
}