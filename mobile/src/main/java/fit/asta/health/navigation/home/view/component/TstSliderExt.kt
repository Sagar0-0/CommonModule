package fit.asta.health.navigation.home.view.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import fit.asta.health.designsystem.components.generic.AppDivider
import fit.asta.health.designsystem.components.generic.AppDividerLineWidth
import fit.asta.health.designsystem.components.generic.AppTexts
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Composable
 fun TstSliderExt() {
    Column(
        Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppTexts.TitleLarge(text = "Why our customers love ASTA?")
        AppDivider(lineWidth = AppDividerLineWidth.TstDividerWidth)
    }
}