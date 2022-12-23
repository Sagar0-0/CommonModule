package fit.asta.health.profile.createprofile.view.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fit.asta.health.profile.view.NextButton
import fit.asta.health.profile.view.PrivacyStatement
import fit.asta.health.profile.view.UserCircleImage
import fit.asta.health.profile.view.UserConsent

@ExperimentalMaterial3Api
@Preview
@Composable
fun DetailsContent() {

    Card(shape = RoundedCornerShape(16.dp)) {

        var text by remember { mutableStateOf(TextFieldValue("")) }
        val focusManager = LocalFocusManager.current

        Column(modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally) {


            Spacer(modifier = Modifier.height(16.dp))

            UserCircleImage()

            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = "Name*",
                    color = Color(0xff132839),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium)
            }

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(value = text,
                onValueChange = {
                    text = it
                },
                shape = RoundedCornerShape(8.dp),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                placeholder = { Text(text = "Astha Puri") })

            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = "Email*",
                    color = Color(0xff132839),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium)
            }

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(value = text,
                onValueChange = {
                    text = it
                },
                shape = RoundedCornerShape(8.dp),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                placeholder = { Text(text = "xyz@gmail.com") })

            Spacer(modifier = Modifier.height(20.dp))

            PrivacyStatement()

            Spacer(modifier = Modifier.height(20.dp))

            UserConsent()

            Spacer(modifier = Modifier.height(20.dp))

            NextButton()

            Spacer(modifier = Modifier.height(16.dp))

        }

    }


}