package fit.asta.health.profile.bottomsheets

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fit.asta.health.profile.bottomsheets.components.AddressType
import fit.asta.health.profile.bottomsheets.components.ButtonListTypes
import fit.asta.health.profile.bottomsheets.components.DividerLine
import fit.asta.health.profile.bottomsheets.components.DoneButton

@Preview
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun InjuryTimeBottomSheet() {


    val buttonTypeList = listOf(ButtonListTypes(buttonType = "3 Months"),
        ButtonListTypes(buttonType = "6 Months"),
        ButtonListTypes(buttonType = "1 Year"))

    val state = rememberBottomSheetScaffoldState()

    BottomSheetScaffold(sheetContent = {
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Column(Modifier
                .fillMaxWidth()
                .padding(top = 32.dp, start = 16.dp, end = 16.dp),
                verticalArrangement = Arrangement.SpaceEvenly) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    DividerLine()
                }
                Spacer(modifier = Modifier.height(20.dp))
                AddressType(radioButtonList = buttonTypeList,
                    selectionTypeText = "Please Select When were you injured")
                Spacer(modifier = Modifier.height(20.dp))
                DoneButton()
            }
        }
    },
        sheetShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        sheetElevation = 10.dp,
        sheetBackgroundColor = MaterialTheme.colorScheme.onPrimary,
        scaffoldState = state,
        drawerBackgroundColor = Color.Green) {
        Text(text = "Hello")
    }

}