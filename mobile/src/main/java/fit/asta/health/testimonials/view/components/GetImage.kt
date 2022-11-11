package fit.asta.health.testimonials.view.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import fit.asta.health.R


@Composable
fun GetImage(
    modifier: Modifier = Modifier,
    beforeSelectedImage: Uri? = null,
    afterSelectedImage: Uri? = null,
    onBeforeImageClick: () -> Unit,
    onAfterImageClick: () -> Unit,
    onBeforeImageClear: () -> Unit,
    onAfterImageClear: () -> Unit,
) {

    Column(modifier = modifier) {
        Text(text = "Add a Photo or Video",
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium)

        Spacer(modifier = Modifier.height(16.dp))

        Box(modifier = Modifier
            .dashedBorder(width = 1.dp, radius = 8.dp, color = Color(0xff8694A9))
            .fillMaxWidth()) {
            Row(horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.padding(0.dp)) {
                ImagePreviewLayout(beforeSelectedImage = beforeSelectedImage,
                    afterSelectedImage = afterSelectedImage,
                    onBeforeImageClick = onBeforeImageClick,
                    onAfterImageClick = onAfterImageClick,
                    onBeforeImageClear = onBeforeImageClear,
                    onAfterImageClear = onAfterImageClear)
            }
        }
    }
}

@Composable
private fun ImagePreviewLayout(
    beforeSelectedImage: Uri? = null,
    afterSelectedImage: Uri? = null,
    onBeforeImageClick: (() -> Unit)? = null,
    onAfterImageClick: (() -> Unit)? = null,
    onBeforeImageClear: () -> Unit,
    onAfterImageClear: () -> Unit,
) {

    var isBeforeDeleted by remember { mutableStateOf(false) }

    var isAfterDeleted by remember { mutableStateOf(false) }


    Box {

        if (beforeSelectedImage != null && !isBeforeDeleted) {
            Box(Modifier
                .padding(2.dp)
                .clickable { onBeforeImageClick?.let { it() } },
                contentAlignment = Alignment.BottomCenter) {

                Image(painter = rememberAsyncImagePainter(model = beforeSelectedImage),
                    contentDescription = null,
                    Modifier
                        .fillMaxWidth(0.5f)
                        .height(180.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop)


                Text(text = "Before Image",
                    fontSize = 16.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(10.dp))

            }

            Box(contentAlignment = Alignment.TopEnd, modifier = Modifier.fillMaxWidth(0.5f)) {
                IconButton(onClick = {
                    onBeforeImageClear()
                    isBeforeDeleted = true
                }) {
                    Icon(painter = painterResource(id = R.drawable.ic_delete_forever),
                        contentDescription = null,
                        tint = Color(0xffFF4081))
                }
            }
        } else {
            UploadBeforeImage(onBeforeImageClick)
            isBeforeDeleted = false
        }

    }


    Box {

        if (afterSelectedImage != null && !isAfterDeleted) {
            Box(Modifier
                .padding(2.dp)
                .clickable { onAfterImageClick?.let { it() } },
                contentAlignment = Alignment.BottomCenter) {

                Image(painter = rememberAsyncImagePainter(model = afterSelectedImage),
                    contentDescription = null,
                    Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop)

                Text(text = "After Image",
                    fontSize = 16.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(10.dp))

            }

            Box(contentAlignment = Alignment.TopEnd, modifier = Modifier.fillMaxWidth()) {
                IconButton(onClick = {
                    onAfterImageClear()
                    isAfterDeleted = true
                }) {
                    Icon(painter = painterResource(id = R.drawable.ic_delete_forever),
                        contentDescription = null,
                        tint = Color(0xffFF4081))
                }
            }
        } else {
            UploadAfterImage(onAfterImageClick)
            isAfterDeleted = false
        }

    }
}

@Composable
private fun UploadAfterImage(onAfterImageClick: (() -> Unit)?) {
    Box(Modifier.padding(2.dp), contentAlignment = Alignment.Center) {
        Card(Modifier
            .fillMaxWidth()
            .height(180.dp)
            .clickable { onAfterImageClick?.let { it() } }) {
            Column(horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()) {
                Image(painter = painterResource(id = R.drawable.upload),
                    contentDescription = null,
                    modifier = Modifier.size(48.dp))
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Browse to choose a File")
            }
        }
    }
}

@Composable
private fun UploadBeforeImage(onBeforeImageClick: (() -> Unit)?) {
    Box(Modifier.padding(2.dp), contentAlignment = Alignment.Center) {
        Card(Modifier
            .fillMaxWidth(0.5f)
            .height(180.dp)
            .clickable { onBeforeImageClick?.let { it() } }) {
            Column(horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()) {
                Image(painter = painterResource(id = R.drawable.upload),
                    contentDescription = null,
                    modifier = Modifier.size(48.dp))
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Browse to choose a File")
            }
        }
    }
}


@Preview
@Composable
fun DemoImage() {

    var beforeSelectedImage by remember { mutableStateOf<Uri?>(null) }
    val beforeLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
            beforeSelectedImage = uri
        }


    var afterSelectedImage by remember { mutableStateOf<Uri?>(null) }
    val afterLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
            afterSelectedImage = uri
        }

    GetImage(onBeforeImageClick = { beforeLauncher.launch("image/jpeg") },
        onAfterImageClick = { afterLauncher.launch("image/jpeg") },
        beforeSelectedImage = beforeSelectedImage,
        afterSelectedImage = afterSelectedImage,
        onBeforeImageClear = { beforeSelectedImage = null },
        onAfterImageClear = { afterSelectedImage = null })

}