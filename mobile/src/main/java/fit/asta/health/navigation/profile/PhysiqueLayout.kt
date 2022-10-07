package fit.asta.health.navigation.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fit.asta.health.R
import fit.asta.health.navigation.profile.components.FemaleLayout
import fit.asta.health.navigation.profile.components.MaleLayout
import fit.asta.health.navigation.profile.components.UserBodyType

var USER_GENDER: String = "Male"

@Preview(showSystemUi = true)
@Composable
fun UserBasicHealthDetail() {
    Column(Modifier
        .fillMaxWidth()
        .padding(vertical = 16.dp, horizontal = 16.dp)
        .verticalScroll(rememberScrollState())
        .background(color = Color.White)) {

        if (USER_GENDER == "Female") FemaleLayout() else MaleLayout()

        Spacer(modifier = Modifier.height(16.dp))
        UserBodyType(bodyType = "BODY TYPE",
            bodyImg = R.drawable.bodyfat,
            bodyStatus = "Fat at Belly and Thighs")
    }
}