package fit.asta.health.navigation.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import fit.asta.health.R
import fit.asta.health.navigation.profile.components.FemaleLayout
import fit.asta.health.navigation.profile.components.MaleLayout
import fit.asta.health.navigation.profile.components.UserBodyType

var USER_GENDER: String = "Male"

@Composable
fun UserBasicHealthDetail(m:Map<String,Any?>) {
    Column(Modifier
        .fillMaxWidth()
        .padding(vertical = 16.dp, horizontal = 16.dp)
        .verticalScroll(rememberScrollState())
        .background(color = Color.White)) {


        if (m["gen"] == "Female") FemaleLayout(m) else MaleLayout(m)

        Spacer(modifier = Modifier.height(16.dp))
        UserBodyType(bodyType = m.keys.last(),
            bodyImg = R.drawable.bodyfat,
            bodyStatus = m.values.last().toString()
        )
    }
}