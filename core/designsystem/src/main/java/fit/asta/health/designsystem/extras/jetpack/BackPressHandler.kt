package fit.asta.health.designsystem.extras.jetpack

import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember

@Composable
fun HandleBackPress(onBackPressed: () -> Unit) {
    val backCallback = remember {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onBackPressed()
            }
        }
    }

    val lifecycleOwner = LocalOnBackPressedDispatcherOwner.current
    DisposableEffect(lifecycleOwner) {
        backCallback.isEnabled = true
        lifecycleOwner?.onBackPressedDispatcher?.addCallback(backCallback)
        onDispose {
            backCallback.remove()
        }
    }
}
