package fit.asta.health.sunlight.feature.screens.skin_conditions.skin_exposure_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
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
import fit.asta.health.sunlight.feature.components.SelectableImageBox
import fit.asta.health.sunlight.feature.event.SkinConditionEvents
import fit.asta.health.sunlight.feature.screens.skin_conditions.util.SkinConditionScreenCode
import fit.asta.health.sunlight.feature.screens.skin_conditions.util.SkinConditionState
import fit.asta.health.sunlight.feature.screens.skin_conditions.util.SkinExposureUtil
import fit.asta.health.sunlight.feature.utils.gridItems
import fit.asta.health.sunlight.remote.model.SkinConditionResponseData
import fit.asta.health.resources.drawables.R as DrawR
import fit.asta.health.resources.strings.R as StringR


@Composable
fun SkinExposureScreen(
    skinConditionDataMapper: MutableMap<String, Value?>,
    state: State<SkinConditionState>,
    onEvent: (SkinConditionEvents) -> Unit,
    onSelect: (SkinConditionResponseData) -> Unit
) {
    LaunchedEffect(Unit) {
        onEvent.invoke(SkinConditionEvents.OnSkinExposure)
    }
    if (state.value.isLoading) {
        AppDotTypingAnimation()
    }
    var selected by remember {
        mutableStateOf(skinConditionDataMapper[SkinConditionScreenCode.EXPOSURE_SCREEN]?.code ?: "")
    }
//    if (skinConditionDataMapper.isNotEmpty()){
//        Log.d("skinConditionMapper", "SkinExposureScreen: $skinConditionDataMapper")
//        LaunchedEffect(Unit){
//            selected=skinConditionDataMapper[SkinConditionScreenCode.EXPOSURE_SCREEN]?.code?:""
//        }
//    }
    Column(
        Modifier
            .fillMaxSize()
            .padding(bottom = 60.dp)
    ) {
        HeadingTexts.Level3(
            text = stringResource(id = StringR.string.skin_exposure_dec),
        )
        LazyColumn {
            gridItems(state.value.skinConditionResponse?.size ?: 0, nColumns = 2) { i ->
                SelectableImageBox(
                    imageLink = state.value.skinConditionResponse?.get(i)?.url,
                    onError =
                    SkinExposureUtil.getExposureDataList().getOrNull(i)?.icon
                        ?: DrawR.drawable.ic_exposure_b2,
                    caption = state.value.skinConditionResponse?.get(i)?.name ?: "-",
                    isSelected = state.value.skinConditionResponse?.get(i)?.code == selected
                ) {
                    skinConditionDataMapper[SkinConditionScreenCode.EXPOSURE_SCREEN] =
                        state.value.skinConditionResponse?.get(i)?.toCommonValue()
                    selected = state.value.skinConditionResponse?.get(i)?.code ?: ""
                    state.value.skinConditionResponse?.get(i)?.let { onSelect.invoke(it) }
                }
            }

        }
    }
}


