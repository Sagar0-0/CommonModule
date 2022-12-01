package fit.asta.health.profile.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fit.asta.health.profile.model.domain.Diet
import fit.asta.health.profile.model.domain.UserPropertyType
import fit.asta.health.profile.view.components.ChipCard
import fit.asta.health.profile.view.components.UpdateButton


// Health Screen Layout

@Composable
fun DietLayout(
    diet: Diet,
    editState: MutableState<Boolean>,
) {

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        Spacer(modifier = Modifier.height(16.dp))

        ChipCard(
            icon = UserPropertyType.FoodAllergies.icon,
            title = UserPropertyType.FoodAllergies.title,
            list = diet.allergies,
            editState = editState
        )

        Spacer(modifier = Modifier.height(16.dp))

        ChipCard(
            icon = UserPropertyType.Cuisines.icon,
            title = UserPropertyType.Cuisines.title,
            list = diet.cuisines,
            editState = editState
        )

        /*Spacer(modifier = Modifier.height(16.dp))

        ChipCard(
            icon = UserPropertyType.NvDays.icon,
            title = UserPropertyType.NvDays.title,
            list = diet.nonVegDays,
            editState = editState
        )

        Spacer(modifier = Modifier.height(16.dp))

        SingleSelectionCard(
            icon = UserPropertyType.DietPref.icon,
            title = UserPropertyType.DietPref.title,
            value = diet.preference,
            editState = editState
        )*/

        Spacer(modifier = Modifier.height(16.dp))

        ChipCard(
            icon = UserPropertyType.FoodRestrictions.icon,
            title = UserPropertyType.FoodRestrictions.title,
            list = diet.foodRestrictions,
            editState = editState
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(Modifier.fillMaxWidth()) {
            if (editState.value) {
                UpdateButton()
            }
        }
    }
}