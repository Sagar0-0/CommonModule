package fit.asta.health.imageCropper.view

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Crop
import androidx.compose.material.icons.filled.CropRotate
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import fit.asta.health.imageCropper.relatedFiles.Cropify
import fit.asta.health.imageCropper.relatedFiles.CropifyOption
import fit.asta.health.imageCropper.relatedFiles.LocalNavigator
import fit.asta.health.imageCropper.relatedFiles.Navigator
import fit.asta.health.imageCropper.relatedFiles.Screen
import fit.asta.health.imageCropper.relatedFiles.rememberCropifyState


@Preview
@Composable
fun ImageSelectionLayout() {

    val navigator = LocalNavigator.current
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { it ->
            if (it.resultCode == Activity.RESULT_OK) {
                it.data?.data?.let { navigator.navigateToFile(it) }
            }
        }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

        Button(onClick = {
            launcher.launch(
                Intent.createChooser(
                    Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                        addCategory(Intent.CATEGORY_OPENABLE)
                        type = "images/*"
                        putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("image/*"))
                    }, null
                )
            )
        }) {
            Text(text = "Click to Select Image")
        }

    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageCropLayout(imageUri: Uri) {

    val cropifyState = rememberCropifyState()
    val cropifyOption by remember { mutableStateOf(CropifyOption()) }
    var croppedImage by remember { mutableStateOf<ImageBitmap?>(null) }

    croppedImage?.let { ImagePreviewDialog(bitmap = it) { croppedImage = null } }

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    "Crop Image", maxLines = 1, overflow = TextOverflow.Ellipsis
                )
            }, navigationIcon = {
                IconButton(onClick = { /* doSomething() */ }) {
                    Icon(
                        imageVector = Icons.Filled.Close, contentDescription = "Close Image Cropper"
                    )
                }
            }, actions = {
                // RowScope here, so these icons will be placed horizontally
                IconButton(onClick = { cropifyState.crop() }) {
                    Icon(
                        imageVector = Icons.Filled.Crop,
                        contentDescription = "Localized description"
                    )
                }
                IconButton(onClick = { cropifyState.crop() }) {
                    Icon(
                        imageVector = Icons.Filled.CropRotate,
                        contentDescription = "Localized description"
                    )
                }
            })
        },
        content = { innerPadding ->

            val context = LocalContext.current

            Cropify(
                uri = imageUri,
                state = cropifyState,
                onImageCropped = { croppedImage = it },
                onFailedToLoadImage = {
                    Toast.makeText(
                        context, "Failed to Load Image", Toast.LENGTH_SHORT
                    ).show()
                },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                option = cropifyOption
            )
        },
        modifier = Modifier.fillMaxSize(),
        contentColor = MaterialTheme.colorScheme.background,
        backgroundColor = MaterialTheme.colorScheme.background,
    )

}


@Composable
fun ImagePreviewDialog(bitmap: ImageBitmap, onDismissRequest: () -> Unit) {
    Dialog(onDismissRequest = onDismissRequest) {
        Image(bitmap = bitmap, contentDescription = "Cropped Image")
    }
}


@Composable
fun CropFAB(modifier: Modifier = Modifier, onClick: () -> Unit) {
    FloatingActionButton(
        onClick = onClick, modifier = modifier
    ) {
        Icon(imageVector = Icons.Filled.Crop, contentDescription = "")
    }
}


@Preview
@Composable
fun LetDemo() {

    Surface(
        modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
    ) {
        val navController = rememberNavController()
        CompositionLocalProvider(LocalNavigator provides Navigator(navController)) {
            NavHost(
                navController = navController,
                startDestination = Screen.Menu.path,
            ) {
                composable(Screen.Menu.path) { ImageSelectionLayout() }
                composable(
                    Screen.File.path, arguments = listOf(navArgument(Screen.File.argUri) {
                        type = NavType.StringType
                    })
                ) {
                    ImageCropLayout(Uri.parse(it.arguments?.getString(Screen.File.argUri)))
                }
            }
        }
    }

}