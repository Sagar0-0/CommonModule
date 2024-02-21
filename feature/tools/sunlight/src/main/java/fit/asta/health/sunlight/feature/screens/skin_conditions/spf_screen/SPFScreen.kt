package fit.asta.health.sunlight.feature.screens.skin_conditions.spf_screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import fit.asta.health.common.utils.Value
import fit.asta.health.designsystem.molecular.animations.AppDotTypingAnimation
import fit.asta.health.designsystem.molecular.texts.HeadingTexts
import fit.asta.health.sunlight.feature.components.SelectableRow
import fit.asta.health.sunlight.feature.screens.skin_conditions.util.SkinConditionScreenCode
import fit.asta.health.sunlight.feature.screens.skin_conditions.util.SkinConditionState
import fit.asta.health.sunlight.remote.model.SkinConditionResponseData
import kotlinx.coroutines.flow.StateFlow
import fit.asta.health.resources.strings.R as StringR

@Composable
fun SPFScreen(
    spfScreenState: StateFlow<SkinConditionState>,
    skinConditionDataMapper: MutableMap<String, Value?>,
    onSelect: (SkinConditionResponseData) -> Unit
) {
    var selected by remember {
        mutableStateOf(
            skinConditionDataMapper[SkinConditionScreenCode.SUNSCREEN_SPF_SCREEN]?.code ?: ""
        )
    }
    val spfState = spfScreenState.collectAsState()
    if (spfState.value.isLoading) {
        AppDotTypingAnimation()
    }
    LazyColumn(
        Modifier
            .fillMaxSize()
            .padding(bottom = 60.dp)
    ) {
        item {
            HeadingTexts.Level3(
                text = stringResource(id = StringR.string.please_select_your_sunscreen_spf),
                modifier = Modifier.padding(8.dp)
            )
        }
        itemsIndexed(spfState.value.skinConditionResponse ?: emptyList()) { index, item ->
            SelectableRow(
                title = item.name ?: "",
                isSelected = item.code == selected
            ) {
                selected = item.code ?: ""
                skinConditionDataMapper[SkinConditionScreenCode.SUNSCREEN_SPF_SCREEN] =
                    item.toCommonValue()
                onSelect.invoke(item)
            }
        }
    }
}