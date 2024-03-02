package fit.asta.health.feature.scheduler.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.request.ImageRequest
import fit.asta.health.common.utils.getImageUrl
import fit.asta.health.common.utils.toStringFromResId
import fit.asta.health.data.scheduler.db.entity.TagEntity
import fit.asta.health.designsystem.AppTheme
import fit.asta.health.designsystem.molecular.button.AppIconButton
import fit.asta.health.designsystem.molecular.cards.AppElevatedCard
import fit.asta.health.designsystem.molecular.image.AppNetworkImage
import fit.asta.health.designsystem.molecular.textfield.AppTextField
import fit.asta.health.designsystem.molecular.texts.TitleTexts
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox
import fit.asta.health.resources.drawables.R as DrawR
import fit.asta.health.resources.strings.R as StringR

@Composable
fun TagCard(text: String, image: String, onClick: () -> Unit = {}) {

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val color = if (isPressed) AppTheme.colors.primary else Color.Transparent

    AppElevatedCard(
        onClick = { onClick() },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(8.dp),
//        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
//        elevation = ButtonDefaults.buttonElevation(5.dp),
//        border = BorderStroke(width = 3.dp, color = color),
//        contentPadding = PaddingValues(0.dp),
//        interactionSource = interactionSource
    ) {
        Row {
            SwipeAbleArea(text, image)
        }
    }
//    AppElevatedButton(
//        onClick = { onClick() },
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(16.dp),
//        shape = RoundedCornerShape(8.dp),
////        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
////        elevation = ButtonDefaults.buttonElevation(5.dp),
//        border = BorderStroke(width = 3.dp, color = color),
//        contentPadding = PaddingValues(0.dp),
//        interactionSource = interactionSource
//    ) {
//        SwipeAbleArea(text, image)
//    }
}

@Composable
private fun SwipeAbleArea(text: String, image: String) {
    Box {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            AppNetworkImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(getImageUrl(url = image))
                    .crossfade(true)
                    .build(),
                errorImage = painterResource(DrawR.drawable.placeholder_tag),
                contentDescription = stringResource(StringR.string.description),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(120.dp)
                    .height(80.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .padding(8.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            TitleTexts.Level2(text = text, color = AppTheme.colors.onSecondaryContainer)
        }
    }
}


@Composable
fun SwipeDemo(
    onSwipe: () -> Unit = {},
    onClick: () -> Unit = {},
    data: TagEntity,
    delete: Boolean = true
) {

    val archive = SwipeAction(
        icon = painterResource(id = DrawR.drawable.ic_baseline_delete_24),
        background = Color.Red,
        onSwipe = { onSwipe() }
    )

    SwipeableActionsBox(
        startActions = if (delete) listOf(archive) else listOf(),
    ) {
        TagCard(text = data.name, image = data.url) { onClick() }
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
            AppIconButton(
                imageVector = Icons.Default.Close,
                modifier = Modifier.size(24.dp)
            ) {
                onNavigateBack()
                keyboardController?.hide()
            }
            TitleTexts.Level2(
                text = stringResource(StringR.string.custom_tags),
                color = AppTheme.colors.onTertiaryContainer,
                textAlign = TextAlign.Center
            )
            AppIconButton(
                imageVector = Icons.Default.Check,
                iconTint = AppTheme.colors.primary
            ) {
                onSave()
                keyboardController?.hide()
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        CustomTagImage(onImageSelect = onImageSelect, image = image)
        Spacer(modifier = Modifier.height(20.dp))
        CustomTagTextField(
            label = StringR.string.tag_name,
            onValueChange = onValueChange
        )

    }
}


@Composable
fun CustomTagImage(image: String, onImageSelect: () -> Unit) {

    Box(contentAlignment = Alignment.Center, modifier = Modifier
        .clickable { onImageSelect() }
        .size(180.dp)
        .clip(CircleShape)
        .border(width = 2.dp, color = AppTheme.colors.primary, shape = CircleShape)) {
        AppNetworkImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(image)
                .crossfade(true)
                .build(),
            errorImage = painterResource(DrawR.drawable.placeholder_tag),
            contentDescription = stringResource(StringR.string.description),
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Image(
            painter = painterResource(id = DrawR.drawable.cameraicon),
            contentDescription = stringResource(StringR.string.camera_icon),
            contentScale = ContentScale.FillBounds
        )
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CustomTagTextField(
    @StringRes label: Int? = null, onValueChange: (String) -> Unit = {},
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done
) {

    var value by remember {
        mutableStateOf("")
    }

    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(key1 = Unit) { focusRequester.requestFocus() }

    AppTextField(
        value = value,
        onValueChange = {
            value = it
            onValueChange(it)
        },
        label = label?.toStringFromResId() ?: "",
//        colors = OutlinedTextFieldDefaults.colors(
//            focusedBorderColor = AppTheme.colors.primary,
//            unfocusedBorderColor = Color.LightGray,
//            focusedLabelColor = AppTheme.colors.primary,
//        ),
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