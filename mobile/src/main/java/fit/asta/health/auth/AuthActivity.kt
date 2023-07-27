package fit.asta.health.auth

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import fit.asta.health.auth.ui.AuthNavHost
import fit.asta.health.auth.viewmodel.AuthViewModel
import fit.asta.health.common.ui.AppTheme
import fit.asta.health.MainActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class AuthActivity : ComponentActivity() {

    companion object {
        fun launch(context: Context) {
            Intent(context, AuthActivity::class.java)
                .apply {
                    context.startActivity(this)
                }
            (context as Activity).finish()
        }
    }

    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadAuthScreen()
    }

    private fun loadAuthScreen() {
        setContent {
            AppTheme {
                val context = LocalContext.current
                val navController = rememberNavController()
                AuthNavHost(
                    navHostController = navController,
                    onSuccess = {
                        if (authViewModel.isAuthenticated()) {
                            Toast.makeText(
                                context, "Sign in Successful", Toast.LENGTH_SHORT
                            ).show()
                            MainActivity.launch(this)
                        }
                    }
                )
            }
        }
    }
}