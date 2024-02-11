@file:OptIn(ExperimentalCoroutinesApi::class, ExperimentalCoroutinesApi::class)

package fit.asta.health.feature.profile.show.view

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
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.feature.profile.create.vm.UserPropertyType
import fit.asta.health.feature.profile.show.view.components.ProfileChipCard
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
        diet.restrictions to UserPropertyType.FoodRestrictions
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = AppTheme.spacing.level2)
            .padding(AppTheme.spacing.level2),
        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.level2)
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
        item { Spacer(modifier = Modifier.height(AppTheme.spacing.level2)) }
    }
}
