@file:OptIn(
    ExperimentalCoroutinesApi::class, ExperimentalCoroutinesApi::class, ExperimentalLayoutApi::class
)

package fit.asta.health.feature.profile.show.view.components

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
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.cards.AppCard
import fit.asta.health.designsystem.molecular.image.AppLocalImage
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import fit.asta.health.feature.profile.show.vm.ProfileViewModel
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
                .padding(AppTheme.spacing.level3)
        ) {
            ProfileHeader(icon, title)
            Spacer(modifier = Modifier.height(AppTheme.spacing.level3))
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
            AppLocalImage(
                painter = painterResource(id = icon),
                contentDescription = "Card Image",
                modifier = Modifier.size(AppTheme.imageSize.level5)
            )
            Spacer(modifier = Modifier.width(AppTheme.spacing.level2))
            TitleTexts.Level3(text = title)
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ChipList(list: List<HealthProperties>) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2),
        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level1)
    ) {
        list.forEach { healthProperty ->
            DisabledChipForList(textOnChip = healthProperty.name)
        }
    }
}
