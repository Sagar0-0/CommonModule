package fit.asta.health.sunlight.feature.screens.skin_conditions.select_medication

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.AppTextField
import fit.asta.health.designsystem.molecular.image.AppLocalImage
import fit.asta.health.designsystem.molecular.texts.BodyTexts
import fit.asta.health.designsystem.molecular.texts.CaptionTexts
import fit.asta.health.resources.drawables.R
import fit.asta.health.sunlight.feature.components.AppDropDown
import fit.asta.health.sunlight.feature.event.SkinConditionEvents
import fit.asta.health.sunlight.feature.viewmodels.SkinConditionViewModel


@Composable
fun SelectMedicationScreen(skinConditionViewModel: SkinConditionViewModel,
                           onEvent:(SkinConditionEvents)->Unit) {
    LaunchedEffect(Unit) {
        skinConditionViewModel.getSupplementPeriod()
        skinConditionViewModel.getUomData()
        onEvent.invoke(SkinConditionEvents.OnSupplements)
    }
    val supplementPeriodState =
        skinConditionViewModel.supplementPeriodState.collectAsStateWithLifecycle()
    val uomState = skinConditionViewModel.uomState.collectAsStateWithLifecycle()
    LocalContext.current
    val isExpanded = remember {
        mutableStateOf(false)
    }
    val isExpanded2 = remember {
        mutableStateOf(false)
    }
    val selectedItem = remember {
        mutableStateOf("")
    }
    if (skinConditionViewModel.intervalOptions.isNotEmpty()) {
        LaunchedEffect(Unit) {
            selectedItem.value = skinConditionViewModel.intervalOptions.first()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        AppDropDown(
            modifier = Modifier.height(100.dp),
            title = "Supplement interval",
            expanded = isExpanded,
            selected = skinConditionViewModel.selectedIntervalOption,
            options = skinConditionViewModel.intervalOptions,
            showAsterisk = false,
            backgroundColor = AppTheme.colors.tertiaryContainer,
            backgroundScale = 10.dp
        ) { index, name ->
        }
        Spacer(modifier = Modifier.height(20.dp))
        BodyTexts.Level1(
            text = "How much dosage do you consume?"
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
                .height(80.dp), horizontalArrangement = Arrangement.Start
        ) {
            AppTextField(
                value = skinConditionViewModel.selectedDosage.value,
                onValueChange = { skinConditionViewModel.selectedDosage.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                keyboardType = KeyboardType.Number,
                trailingIcon = {
                    AppDropDown(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(0.3f)
                            .padding(AppTheme.spacing.level1),
                        expanded = isExpanded2,
                        selected = skinConditionViewModel.selectedUOM,
                        options = uomState.value.skinConditionResponse?.map { it.name ?: "" }
                            ?: listOf(""),
                        showAsterisk = false,
                        backgroundColor = AppTheme.colors.tertiaryContainer,
                        backgroundScale = 2.dp,
                        showTitle = false
                    ) { index, name ->
                    }
                }
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 60.dp, top = AppTheme.spacing.level2)
        ) {
            AppLocalImage(
                painter = painterResource(id = R.drawable.ic_medical_care_movn),
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(0.9f)
            )
            CaptionTexts.Level1(
                text = "*Please proceed if you don't consume any*",
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }

    }

}