@file:OptIn(ExperimentalCoroutinesApi::class, ExperimentalCoroutinesApi::class)

package fit.asta.health.profile.feature.show.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fit.asta.health.data.profile.remote.model.Diet
import fit.asta.health.designsystemx.AstaThemeX
import fit.asta.health.profile.feature.create.vm.UserPropertyType
import fit.asta.health.profile.feature.show.view.components.ProfileChipCard
import kotlinx.coroutines.ExperimentalCoroutinesApi


// Health Screen Layout

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun DietLayout(
    diet: Diet,
) {
    val userPropertyList = listOf(
        diet.preference to UserPropertyType.DietPref,
        diet.nonVegDays to UserPropertyType.NvDays,
        diet.allergies to UserPropertyType.FoodAllergies,
        diet.cuisines to UserPropertyType.Cuisines,
        diet.foodRestrictions to UserPropertyType.FoodRestrictions
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = AstaThemeX.spacingX.medium)
            .padding(AstaThemeX.spacingX.medium),
        verticalArrangement = Arrangement.spacedBy(AstaThemeX.spacingX.medium)
    ) {
        items(userPropertyList) { (property, type) ->
            property?.let {
                ProfileChipCard(
                    icon = type.icon,
                    title = type.getTitle(),
                    list = it,
                )
            }
        }

        item { Spacer(modifier = Modifier.height(AstaThemeX.spacingX.medium)) }
    }
}
