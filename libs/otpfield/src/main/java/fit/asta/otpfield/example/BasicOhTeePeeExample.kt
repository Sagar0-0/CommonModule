package fit.asta.otpfield.example

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fit.asta.otpfield.OhTeePeeInput
import fit.asta.otpfield.configuration.OhTeePeeCellConfiguration
import fit.asta.otpfield.configuration.OhTeePeeConfigurations

@Composable
internal fun BasicOhTeePeeExample() {
    var otpValue: String by remember { mutableStateOf("12") }

    val defaultConfig = OhTeePeeCellConfiguration.withDefaults(
        borderColor = Color.LightGray,
        borderWidth = 1.dp,
        shape = RoundedCornerShape(16.dp),
        textStyle = TextStyle(
            color = Color.Black
        )
    )

    OhTeePeeInput(
        value = otpValue,
        onValueChange = { newValue, isValid ->
            otpValue = newValue
            if (isValid) {
                // Validate the value here...
            }
        },
        /* when the value is 1111, all cells will use errorCellConfig */
        isValueInvalid = otpValue == "1111",
        configurations = OhTeePeeConfigurations.withDefaults(
            cellsCount = 6,
            emptyCellConfig = defaultConfig,
            filledCellConfig = defaultConfig,
            activeCellConfig = defaultConfig.copy(
                borderColor = Color.Black, borderWidth = 2.dp
            ),
            errorCellConfig = defaultConfig.copy(
                borderColor = Color.Red, borderWidth = 2.dp
            ),
            placeHolder = "-",
            cellModifier = Modifier
                .padding(horizontal = 4.dp)
                .size(48.dp),
        ),
    )
}

@Preview
@Composable
private fun BasicOhTeePeePreview() {
    MaterialTheme {
        BasicOhTeePeeExample()
    }
}