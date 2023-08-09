@file:OptIn(ExperimentalCoroutinesApi::class, ExperimentalCoroutinesApi::class)

package fit.asta.health.profile.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
            .padding(top = spacing.medium)
            .padding(spacing.medium),
        verticalArrangement = Arrangement.spacedBy(spacing.medium)
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

        item { Spacer(modifier = Modifier.height(spacing.medium)) }
    }
}
