@file:OptIn(ExperimentalCoroutinesApi::class, ExperimentalCoroutinesApi::class)

package fit.asta.health.profile.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.profile.model.domain.Diet
import fit.asta.health.profile.model.domain.UserPropertyType
import fit.asta.health.profile.view.components.ProfileChipCard
import kotlinx.coroutines.ExperimentalCoroutinesApi


// Health Screen Layout

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun DietLayout(
    diet: Diet,
) {

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxWidth()
            .padding(spacing.medium)
    ) {
        Spacer(modifier = Modifier.height(spacing.medium))
        diet.preference?.let {
            ProfileChipCard(
                icon = UserPropertyType.DietPref.icon,
                title = UserPropertyType.DietPref.title,
                list = it,
            )
        }
        Spacer(modifier = Modifier.height(spacing.medium))
        diet.nonVegDays?.let {
            ProfileChipCard(
                icon = UserPropertyType.NvDays.icon,
                title = UserPropertyType.NvDays.title,
                list = it,
            )
        }
        Spacer(modifier = Modifier.height(spacing.medium))
        diet.allergies?.let {
            ProfileChipCard(
                icon = UserPropertyType.FoodAllergies.icon,
                title = UserPropertyType.FoodAllergies.title,
                list = it,
            )
        }
        Spacer(modifier = Modifier.height(spacing.medium))
        diet.cuisines?.let {
            ProfileChipCard(
                icon = UserPropertyType.Cuisines.icon,
                title = UserPropertyType.Cuisines.title,
                list = it,
            )
        }
        Spacer(modifier = Modifier.height(spacing.medium))
        diet.foodRestrictions?.let {
            ProfileChipCard(
                icon = UserPropertyType.FoodRestrictions.icon,
                title = UserPropertyType.FoodRestrictions.title,
                list = it,
            )
        }
        Spacer(modifier = Modifier.height(spacing.medium))
    }
}