package fit.asta.health.sunlight.feature.screens.skin_conditions.skin_color_screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import fit.asta.health.sunlight.feature.components.SelectableRowSkinColor
import fit.asta.health.sunlight.feature.event.SkinConditionEvents
import fit.asta.health.sunlight.feature.screens.skin_conditions.util.SkinColorData
import fit.asta.health.sunlight.feature.screens.skin_conditions.util.SkinConditionScreenCode
import fit.asta.health.sunlight.feature.screens.skin_conditions.util.SkinConditionState
import fit.asta.health.sunlight.remote.model.SkinConditionResponseData
import kotlinx.coroutines.flow.StateFlow
import fit.asta.health.resources.strings.R as StringR


@Composable
fun SelectSkinColorScreen(
    skinColorState: StateFlow<SkinConditionState>,
    skinColorsData: List<SkinColorData>,
    skinConditionDataMapper: MutableMap<String, Value?>,
    onEvent: (SkinConditionEvents) -> Unit,
    onSelect: (SkinConditionResponseData) -> Unit
) {
    val skinColor=skinColorState.collectAsState()
    if(skinColor.value.isLoading){
        AppDotTypingAnimation()
    }
    LaunchedEffect(Unit){
        onEvent.invoke(SkinConditionEvents.OnSkinColor)
    }
    var selected by remember {
        mutableStateOf(skinConditionDataMapper[SkinConditionScreenCode.EXPOSURE_SCREEN]?.code ?: "")
    }
    if (skinConditionDataMapper.isNotEmpty()) {
        LaunchedEffect(Unit) {
            selected =
                skinConditionDataMapper[SkinConditionScreenCode.SKIN_COLOR_SCREEN]?.code ?: ""
        }
    }
    LazyColumn(
        Modifier
            .fillMaxSize()
            .padding(bottom = 60.dp)
    ) {
        item {
            HeadingTexts.Level3(
                text = stringResource(id = StringR.string.please_select_your_skin_color),
                modifier = Modifier.padding(8.dp)
            )
        }
        itemsIndexed(skinColor.value.skinConditionResponse ?: emptyList()) { index, item ->
            SelectableRowSkinColor(
                title = item.name ?: "-", unSelectedColor = skinColorsData[index].color,
                isSelected = item.code == selected
            ) {
                selected = item.code ?: ""
                skinConditionDataMapper[SkinConditionScreenCode.SKIN_COLOR_SCREEN] =
                    item.toCommonValue()
                onSelect.invoke(item)
            }
        }
    }
}
