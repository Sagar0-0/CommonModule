package fit.asta.health.feature.profile.basic.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.delay

@Preview
@Composable
fun Demo() {

//    val keyboardController = LocalSoftwareKeyboardController.current  **Issue Exist here

// used to ensure a TextField is focused when showing keyboard
    val focusRequester = remember { FocusRequester() }
    val (text, setText) = remember {
        mutableStateOf("Close keyboard on done ime action (blue ✔️)")
    }

    Column(Modifier.padding(16.dp)) {
        BasicTextField(
            text,
            setText,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { }), //Issue exist here
            modifier = Modifier
                .focusRequester(focusRequester)
                .fillMaxWidth()
        )
        Spacer(Modifier.height(16.dp))
        Button(
            onClick = {
                focusRequester.requestFocus()
            }, modifier = Modifier.fillMaxWidth()
        ) {
            Text("Show software keyboard.")
        }
    }

    LaunchedEffect(focusRequester) {
        awaitFrame()
        delay(1000)
        focusRequester.requestFocus()
    }
}