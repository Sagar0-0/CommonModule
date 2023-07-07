@file:OptIn(ExperimentalMaterialApi::class, ExperimentalMaterialApi::class)

package fit.asta.health.imageCropperV2.demo

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Crop
import androidx.compose.material.icons.filled.LockReset
import androidx.compose.material.icons.filled.Rotate90DegreesCw
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smarttoolfactory.colorpicker.widget.drawChecker
import fit.asta.health.imageCropperV2.ImageSelectionButton
import fit.asta.health.imageCropperV2.cropper.ImageCropper

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContent() {

    var angle by remember {
        mutableFloatStateOf(0f)
    }

    var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }
    var croppedImage by remember { mutableStateOf<ImageBitmap?>(null) }

    var crop by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    var isCropping by remember { mutableStateOf(false) }


    Scaffold(
        topBar = {
            TopAppBar(title = {
                androidx.compose.material.Text(
                    "Crop Image", maxLines = 1, overflow = TextOverflow.Ellipsis
                )
            }, navigationIcon = {
                IconButton(onClick = { /* doSomething() */ }) {
                    Icon(
                        imageVector = Icons.Filled.Close, contentDescription = "Close Image Cropper"
                    )
                }
            })
        },
        bottomBar = {
            BottomAppBar(actions = {
                IconButton(onClick = { crop = true }) {
                    Icon(Icons.Filled.Crop, contentDescription = "Crop")
                }
                IconButton(onClick = { angle = (angle + 90) % 360f }) {
                    Icon(
                        Icons.Filled.Rotate90DegreesCw, contentDescription = ""
                    )
                }
                IconButton(onClick = { imageBitmap = null }) {
                    Icon(
                        Icons.Filled.LockReset,
                        contentDescription = "delete image",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }, floatingActionButton = {
                ImageSelectionButton(elevation = FloatingActionButtonDefaults.elevation(
                    defaultElevation = 0.dp
                ), onImageSelected = { bitmap: ImageBitmap ->
                    imageBitmap = bitmap
                })
            })
        },
        content = { innerPadding ->

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(Color.DarkGray),
                contentAlignment = Alignment.Center
            ) {
                Column(modifier = Modifier.fillMaxSize()) {

                    imageBitmap?.let { bitMap ->
                        ImageCropper(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            imageBitmap = bitMap,
                            contentDescription = "Image Cropper",
                            crop = crop,
                            onCropStart = {
                                isCropping = true
                            },
                            onCropSuccess = {
                                croppedImage = it
                                isCropping = false
                                crop = false
                                showDialog = true
                            },
                            angle = angle
                        )
                    }
                }

                if (isCropping) {
                    CircularProgressIndicator()
                }
            }

            if (showDialog) {
                croppedImage?.let {
                    ShowCroppedImageDialog(imageBitmap = it) {
                        showDialog = !showDialog
                        croppedImage = null
                    }
                }
            }


        },
        modifier = Modifier.fillMaxSize(),
        contentColor = MaterialTheme.colorScheme.background,
        backgroundColor = MaterialTheme.colorScheme.background,
    )


}

@Composable
private fun ShowCroppedImageDialog(imageBitmap: ImageBitmap, onDismissRequest: () -> Unit) {
    AlertDialog(onDismissRequest = onDismissRequest, text = {
        Image(
            modifier = Modifier
                .drawChecker(RoundedCornerShape(8.dp))
                .fillMaxWidth()
                .aspectRatio(1f),
            contentScale = ContentScale.Fit,
            bitmap = imageBitmap,
            contentDescription = "result"
        )
    }, confirmButton = {
        TextButton(onClick = {
            onDismissRequest()
        }) {
            Text("Confirm")
        }
    }, dismissButton = {
        TextButton(onClick = {
            onDismissRequest()
        }) {
            Text("Dismiss")
        }
    })
}