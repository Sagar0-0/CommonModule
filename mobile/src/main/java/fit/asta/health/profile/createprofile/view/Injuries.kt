@file:OptIn(ExperimentalCoroutinesApi::class)

package fit.asta.health.profile.createprofile.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import fit.asta.health.profile.model.domain.HealthProperties
import fit.asta.health.profile.model.domain.TwoRadioBtnSelections
import fit.asta.health.profile.viewmodel.ProfileViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@Composable
fun ValidateListError(
    viewModel: ProfileViewModel = hiltViewModel(),
    selectedOption: TwoRadioBtnSelections?,
    cardList: SnapshotStateList<HealthProperties>,
    listName: String,
) {
    Row(Modifier.fillMaxWidth()) {
        if (selectedOption is TwoRadioBtnSelections.First && cardList.isEmpty()) {
            Text(
                text = viewModel.validateDataList(
                    list = cardList, listName = listName
                ).asString(),
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.titleSmall
            )
        }
    }
}