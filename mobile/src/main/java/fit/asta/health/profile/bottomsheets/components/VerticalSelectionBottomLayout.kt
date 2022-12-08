package fit.asta.health.profile.bottomsheets.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize


@Composable
fun AutoComplete(exerciseList: List<String>) {

    var exercise by remember {
        mutableStateOf("")
    }

    var textFieldSize by remember {
        mutableStateOf(Size.Zero)
    }

    val interactionSource = remember {
        MutableInteractionSource()
    }

    //Category Field

    Column(modifier = Modifier
        .padding(top = 32.dp, start = 16.dp, end = 16.dp)
        .fillMaxWidth()
        .clickable(onClick = { }, interactionSource = interactionSource, indication = null)) {

        Column(Modifier
            .fillMaxWidth()
            .height(450.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
            DividerLine()
            Spacer(modifier = Modifier.height(20.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                TextField(value = exercise,
                    onValueChange = {
                        exercise = it
                    },
                    placeholder = {
                        Text(text = "Search", fontSize = 16.sp, color = Color(0xff999999))
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(width = 1.8.dp,
                            color = Color.Black,
                            shape = RoundedCornerShape(8.dp))
                        .onGloballyPositioned { coordinates ->
                            textFieldSize = coordinates.size.toSize()
                        }
                        .navigationBarsPadding()
                        .imePadding(),
                    colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = Color(0xffDFE6ED)),
                    textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done),
                    singleLine = true,
                    trailingIcon = {
                        IconButton(onClick = {
                            TODO()
                        }) {
                            Icon(imageVector = Icons.Default.Search, contentDescription = null)
                        }
                    })
            }
            Spacer(modifier = Modifier.height(16.dp))
            SearchKeywords(textFieldSize, exercise, exerciseList)
        }
        DoneButton()
    }
}

@Composable
private fun SearchKeywords(
    textFieldSize: Size,
    exercise: String,
    exerciseList: List<String>,
) {

    var exercise1 = exercise
    LazyColumn(modifier = Modifier
        .padding(horizontal = 5.dp)
        .width(textFieldSize.width.dp),
        contentPadding = PaddingValues(vertical = 0.dp)) {

        if (exercise1.isNotEmpty()) {
            items(exerciseList.filter {
                it.lowercase().contains(exercise1.lowercase())
            }.sorted()) {
                LayoutItems(title = it) { title ->
                    exercise1 = title
                }
            }
        } else {
            items(exerciseList.sorted()) {
                LayoutItems(title = it) { title ->
                    exercise1 = title
                }
            }
        }
    }
}


@Composable
fun LayoutItems(
    title: String,
    onSelect: (String) -> Unit,
) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .clickable { onSelect(title) }
        .padding(10.dp)) {
        Text(text = title, fontSize = 16.sp)
    }
}