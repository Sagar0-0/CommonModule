@file:OptIn(ExperimentalCoroutinesApi::class, ExperimentalCoroutinesApi::class)

package fit.asta.health.profile.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fit.asta.health.profile.model.domain.Diet
import fit.asta.health.profile.model.domain.UserPropertyType
import fit.asta.health.profile.view.components.ChipCard
import kotlinx.coroutines.ExperimentalCoroutinesApi


// Health Screen Layout

@Composable
fun DietLayout(
    diet: Diet,
) {

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        Spacer(modifier = Modifier.height(16.dp))

        diet.preference?.let {
            ChipCard(
                icon = UserPropertyType.DietPref.icon,
                title = UserPropertyType.DietPref.title,
                list = it,
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        diet.nonVegDays?.let {
            ChipCard(
                icon = UserPropertyType.NvDays.icon,
                title = UserPropertyType.NvDays.title,
                list = it,
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        diet.allergies?.let {
            ChipCard(
                icon = UserPropertyType.FoodAllergies.icon,
                title = UserPropertyType.FoodAllergies.title,
                list = it,
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        diet.cuisines?.let {
            ChipCard(
                icon = UserPropertyType.Cuisines.icon,
                title = UserPropertyType.Cuisines.title,
                list = it,
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        diet.foodRestrictions?.let {
            ChipCard(
                icon = UserPropertyType.FoodRestrictions.icon,
                title = UserPropertyType.FoodRestrictions.title,
                list = it,
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}