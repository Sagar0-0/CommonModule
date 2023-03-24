@file:OptIn(ExperimentalCoroutinesApi::class)

package fit.asta.health.profile.bottomsheets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.flowlayout.FlowRow
import fit.asta.health.common.ui.theme.spacing
import fit.asta.health.profile.bottomsheets.components.DividerLine
import fit.asta.health.profile.model.domain.HealthProperties
import fit.asta.health.profile.view.components.AddChipOnCard
import fit.asta.health.profile.viewmodel.ProfileEvent
import fit.asta.health.profile.viewmodel.ProfileViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@Composable
fun ItemSelectionBtmSheetLayout(
    viewModel: ProfileViewModel = hiltViewModel(),
    cardList: List<HealthProperties>? = null,
) {

    val focusManager = LocalFocusManager.current

    Box(
        Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        Column(Modifier.fillMaxWidth()) {

            Spacer(modifier = Modifier.height(spacing.medium))

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                DividerLine()
            }

            Row(
                modifier = Modifier.padding(spacing.medium)
            ) {

                val searchQuery = remember { mutableStateOf("") }

                OutlinedTextField(

                    value = searchQuery.value,
                    onValueChange = { searchQuery.value = it },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("Search") },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = null,
                            tint = Color.Gray,
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                    shape = MaterialTheme.shapes.medium

                )

            }

            FlowRow(
                mainAxisSpacing = spacing.minSmall,
                modifier = Modifier.padding(start = spacing.medium),
            ) {

                cardList?.forEach {
                    AddChipOnCard(textOnChip = it.name, onClick = {
                        viewModel.onEvent(ProfileEvent.SetSelectedAddItemOption(it))
                    })
                }
            }

        }
    }


}

