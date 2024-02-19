package fit.asta.health.sunlight.feature.screens.skin_conditions.skin_type_screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import fit.asta.health.common.utils.Value
import fit.asta.health.designsystem.molecular.texts.HeadingTexts
import fit.asta.health.sunlight.feature.components.SelectableRow
import fit.asta.health.sunlight.feature.screens.skin_conditions.util.SkinConditionScreenCode
import fit.asta.health.sunlight.feature.screens.skin_conditions.util.SkinConditionState
import fit.asta.health.sunlight.remote.model.SkinConditionResponseData
import kotlinx.coroutines.flow.StateFlow
import fit.asta.health.resources.strings.R as StringR

@Composable
fun SkinTypeScreen(
    skinTypeState: StateFlow<SkinConditionState>,
    skinConditionDataMapper: MutableMap<String, Value?>,
    onSelect: (SkinConditionResponseData) -> Unit
) {
    var selected by remember {
        mutableStateOf(
            skinConditionDataMapper[SkinConditionScreenCode.SKIN_TYPE_SCREEN]?.code ?: ""
        )
    }
    LazyColumn(
        Modifier
            .fillMaxSize()
            .padding(bottom = 60.dp)
    ) {
        item {
            HeadingTexts.Level3(
                text = stringResource(id = StringR.string.please_select_your_skin_type),
                modifier = Modifier.padding(8.dp)
            )
        }
        itemsIndexed(skinTypeState.value.skinConditionResponse ?: emptyList()) { index, item ->
            SelectableRow(
                title = item.name ?: "",
                isSelected = item.code == selected
            ) {
                selected = item.code ?: ""
                skinConditionDataMapper[SkinConditionScreenCode.SKIN_TYPE_SCREEN] =
                    item.toCommonValue()
                onSelect.invoke(item)
            }
        }
    }
}