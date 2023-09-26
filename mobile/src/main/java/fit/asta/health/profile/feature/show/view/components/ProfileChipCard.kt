@file:OptIn(
    ExperimentalCoroutinesApi::class, ExperimentalCoroutinesApi::class, ExperimentalLayoutApi::class
)

package fit.asta.health.profile.feature.show.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import fit.asta.health.data.profile.remote.model.HealthProperties
import fit.asta.health.designsystem.components.generic.AppCard
import fit.asta.health.designsystem.components.generic.AppDrawImg
import fit.asta.health.designsystem.components.generic.AppTexts
import fit.asta.health.designsystemx.AstaThemeX
import fit.asta.health.profile.feature.show.vm.ProfileViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi


@Composable
fun ProfileChipCard(
    viewModel: ProfileViewModel = hiltViewModel(),
    icon: Int,
    title: String,
    list: List<HealthProperties>,
) {
    AppCard {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(AstaThemeX.spacingX.medium)
        ) {
            ProfileHeader(icon, title)
            Spacer(modifier = Modifier.height(AstaThemeX.spacingX.medium))
            ChipList(list)
        }
    }
}

@Composable
private fun ProfileHeader(icon: Int, title: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AppDrawImg(
                painter = painterResource(id = icon),
                contentDescription = "Card Image",
                modifier = Modifier.size(AstaThemeX.imageSize.largeMedium)
            )
            Spacer(modifier = Modifier.width(AstaThemeX.spacingX.small))
            AppTexts.BodySmall(text = title)
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ChipList(list: List<HealthProperties>) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(AstaThemeX.spacingX.small),
        verticalArrangement = Arrangement.spacedBy(AstaThemeX.spacingX.minSmall)
    ) {
        list.forEach { healthProperty ->
            DisabledChipForList(textOnChip = healthProperty.name)
        }
    }
}
