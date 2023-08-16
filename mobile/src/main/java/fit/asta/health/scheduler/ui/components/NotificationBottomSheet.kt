package fit.asta.health.scheduler.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fit.asta.health.R


@Composable
fun NotificationBottomSheetLayout(
    text: String,
    onNavigateBack: () -> Unit,
    onSave:(String)->Unit={}
) {

    val radioOptions =
        listOf(stringResource(id = R.string.notification), stringResource(id = R.string.splash))
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }


    Column(
        Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = onNavigateBack) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = null
                )
            }
           Text(
                text = text,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onTertiaryContainer,
                textAlign = TextAlign.Center
            )
           IconButton(onClick = { onSave(selectedOption) }) {
               Icon(
                   imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }

        radioOptions.forEach { text ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp), verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (text == selectedOption),
                    onClick = {
                        onOptionSelected(text)
                    }
                )
                Text(
                    text = text,
                    modifier = Modifier.padding(start = 8.dp),
                    fontSize = 16.sp
                )
            }
        }
    }
}