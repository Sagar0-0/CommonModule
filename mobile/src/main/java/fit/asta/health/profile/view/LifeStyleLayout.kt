package fit.asta.health.profile.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fit.asta.health.profile.model.domain.LifeStyle
import fit.asta.health.profile.view.components.UpdateButton


// Health Screen Layout

@Composable
fun LifeStyleLayout(
    lifeStyle: LifeStyle,
    checkedState: MutableState<Boolean>,
) {

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        Spacer(modifier = Modifier.height(16.dp))

        Row(Modifier.fillMaxWidth()) {
            if (checkedState.value) {
                UpdateButton()
            }
        }


    }
}