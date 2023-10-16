package fit.asta.ccp.example

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fit.asta.ccp.ui.CountryCodePicker
import fit.asta.ccp.ui.getErrorStatus
import fit.asta.ccp.ui.getFullPhoneNumber
import fit.asta.ccp.ui.getOnlyPhoneNumber
import fit.asta.ccp.ui.isPhoneNumber
import fit.asta.health.designsystem.AppTheme

@Composable
fun CountryCodePick() {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val phoneNumber = rememberSaveable { mutableStateOf("") }
        val fullPhoneNumber = rememberSaveable { mutableStateOf("") }
        val onlyPhoneNumber = rememberSaveable { mutableStateOf("") }

        CountryCodePicker(
            text = phoneNumber.value,
            onValueChange = { phoneNumber.value = it },
            unfocusedBorderColor = AppTheme.colors.primary,
            bottomStyle = false,
            shape = RoundedCornerShape(24.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = {
            if (isPhoneNumber()) {
                fullPhoneNumber.value = getFullPhoneNumber()
                onlyPhoneNumber.value = getOnlyPhoneNumber()
            } else {
                fullPhoneNumber.value = "Error"
                onlyPhoneNumber.value = "Error"
            }
        }) {
            Text(text = "Check")
        }

        Text(
            text = "Full Phone Number: ${fullPhoneNumber.value}",
            color = if (getErrorStatus()) Color.Red else Color.Green
        )

        Text(
            text = "Only Phone Number: ${onlyPhoneNumber.value}",
            color = if (getErrorStatus()) Color.Red else Color.Green
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun CountryCodePickerPreview() {
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = { TopAppBar(title = { Text(text = "Country Code Picker") }) }) { top ->
        top.calculateTopPadding()
        Surface(modifier = Modifier.fillMaxSize()) {
            CountryCodePick()
        }
    }
}