@file:OptIn(ExperimentalCoroutinesApi::class)

package fit.asta.health.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import dagger.hilt.android.AndroidEntryPoint
import fit.asta.health.common.ui.AppTheme
import fit.asta.health.profile.viewmodel.ProfileViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class CreateUserProfileActivity : AppCompatActivity() {

    private lateinit var viewModel: ProfileViewModel

    companion object {

        fun launch(context: Context) {
            val intent = Intent(context, CreateUserProfileActivity::class.java)
            intent.apply {
                context.startActivity(this)
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                CreateProfileLayout()
            }
        }
    }


}

@Preview
@Composable
fun DemoView() {

    val context = LocalContext.current

    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Button(onClick = { CreateUserProfileActivity.launch(context) }) {
            Text(text = "Click Me")
        }
    }

}