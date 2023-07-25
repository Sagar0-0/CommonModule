package fit.asta.health.scheduler.compose.components

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import fit.asta.health.R
import fit.asta.health.common.utils.getImageUrl
import fit.asta.health.scheduler.model.db.entity.TagEntity
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@Composable
fun TagCard(text: String, image: String, onClick: () -> Unit = {}) {

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val color = if (isPressed) MaterialTheme.colorScheme.primary else Color.Transparent

    Button(
        onClick = { onClick() },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = ButtonDefaults.buttonElevation(5.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
        interactionSource = interactionSource,
        contentPadding = PaddingValues(0.dp),
        border = BorderStroke(width = 3.dp, color = color)
    ) {
        SwipeAbleArea(text, image)
    }
}

@Composable
private fun SwipeAbleArea(text: String, image: String) {
    Box {//"https://img2.asta.fit${image}"
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(getImageUrl(url = image))
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.placeholder_tag),
                contentDescription = stringResource(R.string.description),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(120.dp)
                    .height(80.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .padding(8.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = text,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
    }
}


@Composable
fun SwipeDemo(onSwipe: () -> Unit = {}, onClick: () -> Unit = {}, data: TagEntity) {

    val archive = SwipeAction(
        icon = painterResource(id = R.drawable.ic_baseline_delete_24),
        background = Color.Red,
        onSwipe = { onSwipe() }
    )

    SwipeableActionsBox(
        startActions = listOf(archive),
    ) {
        TagCard(text = data.meta.name, image = data.meta.url) { onClick() }
    }

}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CustomTagBottomSheetLayout(
    image: String,
    onNavigateBack: () -> Unit,
    onSave: () -> Unit,
    onImageSelect: () -> Unit,
    onValueChange: (String) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Column(
        Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = {
                onNavigateBack()
                keyboardController?.hide()
            }) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = null,
                    Modifier.size(24.dp)
                )
            }
            Text(
                text = "Custom Tags",
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onTertiaryContainer,
                textAlign = TextAlign.Center
            )
            IconButton(onClick = {
                onSave()
                keyboardController?.hide()
            }) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        CustomTagImage(onImageSelect = onImageSelect, image = image)
        Spacer(modifier = Modifier.height(20.dp))
        CustomTagTextField(label = "Tag Name", onValueChange = onValueChange)

    }
}


@Composable
fun CustomTagImage(image: String, onImageSelect: () -> Unit) {

    IconButton(
        onClick = onImageSelect, modifier = Modifier
            .size(180.dp)
            .clip(CircleShape)
            .border(width = 2.dp, color = MaterialTheme.colorScheme.primary, shape = CircleShape)
    ) {
        Box(contentAlignment = Alignment.Center) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(image)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.placeholder_tag),
                contentDescription = stringResource(R.string.description),
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Image(
                painter = painterResource(id = R.drawable.cameraicon),
                contentDescription = "Camera Icon",
                contentScale = ContentScale.FillBounds
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun CustomTagTextField(
    label: String, onValueChange: (String) -> Unit = {},
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done
) {

    var value by remember {
        mutableStateOf("")
    }

    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(key1 = Unit) { focusRequester.requestFocus() }

    OutlinedTextField(
        value = value,
        onValueChange = {
            value = it
            onValueChange(it)
        },
        label = { Text(label) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = Color.LightGray,
            focusedLabelColor = MaterialTheme.colorScheme.primary
        ),
        modifier = Modifier.focusRequester(focusRequester = focusRequester),
        shape = RoundedCornerShape(8.dp),
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        keyboardActions = KeyboardActions(onDone = {
            focusRequester.freeFocus()
            keyboardController?.hide()
        })
    )

}
