package fit.asta.health.sunlight.feature.screens.selfcare_suggetion.selfcare_suggetion_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.background.AppScaffold
import fit.asta.health.designsystem.molecular.background.AppTopBar
import fit.asta.health.designsystem.molecular.texts.CaptionTexts
import fit.asta.health.designsystem.molecular.texts.HeadingTexts
import fit.asta.health.sunlight.feature.screens.selfcare_suggetion.component.SelfCareSuggestionCardFood
import fit.asta.health.sunlight.feature.screens.selfcare_suggetion.component.SelfCareSuggestionCardSupplements
import fit.asta.health.sunlight.remote.model.HelpAndNutrition

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelfCareSuggestionScreen(
    data: HelpAndNutrition,
    onBack: () -> Unit,
) {
    val scrollState = rememberScrollState()
    AppScaffold(
        topBar = {
            AppTopBar(
                title = "Help and Suggestions",
                onBack = onBack,
                containerColor = AppTheme.colors.surface
            )
        },
        modifier = Modifier.background(AppTheme.colors.surface)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    start = AppTheme.spacing.level2,
                    end = AppTheme.spacing.level2,
                    bottom = AppTheme.spacing.level2
                )
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)
        ) {
            HeadingTexts.Level1(text = "Supplements")
            LazyRow {
                items(data.suppl) { item ->
                    SelfCareSuggestionCardSupplements(item)
                }
            }
            HeadingTexts.Level1(text = "Foods")
            LazyRow {
                items(data.food) { item ->
                    SelfCareSuggestionCardFood(item)
                }
            }
            HeadingTexts.Level1(text = "Beverages")
            LazyRow {
                items(data.bev) { item ->
                    SelfCareSuggestionCardFood(item)
                }
            }
            CaptionTexts.Level3(text = "Caution:- ${data.msg ?: ""}")

        }
    }
}