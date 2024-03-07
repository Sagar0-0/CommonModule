package fit.asta.health.sunlight.feature.screens.skin_conditions.age_range_screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.sunlight.feature.components.SelectableRow
import fit.asta.health.sunlight.feature.screens.skin_conditions.util.AgeUtil
import fit.asta.health.resources.strings.R as StrR


@Composable
fun AgeRangeScreen() {
    var selected by remember {
        mutableIntStateOf(0)
    }
    LazyColumn(
        Modifier
            .fillMaxSize()
            .padding(bottom = 60.dp)
    ) {
        item {
            TitleTexts.Level2(
                text = stringResource(id = StrR.string.please_select_your_age_range),
                style = AppTheme.customTypography.body.level2
                    .copy(Color.White),
                modifier = Modifier.padding(8.dp)
            )
        }
        itemsIndexed(AgeUtil.ageList) { index, item ->
            SelectableRow(
                title = item.title,
                isSelected = index == selected
            ) {
                //handle on click
                selected = index
            }
        }
    }
}
