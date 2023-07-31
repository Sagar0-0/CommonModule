package fit.asta.health.navigation.home.view.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fit.asta.health.common.ui.components.generic.AppTexts
import fit.asta.health.common.ui.theme.spacing

@Composable
fun ViewAllLayout(
    myTools: String,
    allTools: String,
    onClick: () -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = spacing.small)
    ) {
        AppTexts.TitleMedium(cardTitle = myTools)
        Box {
            AppTexts.TitleLarge(cardTitle = allTools, modifier = Modifier.clickable { onClick() })
        }
    }
}