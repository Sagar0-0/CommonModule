package fit.asta.health.tools.walking.view

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.BottomNavigation
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import fit.asta.health.R
import fit.asta.health.tools.walking.nav.StepsCounterNavigation
import fit.asta.health.tools.walking.view.component.WalkingBottomSheet
import fit.asta.health.tools.walking.viewmodel.WalkingViewModel
import fit.asta.health.tools.water.WaterToolActivity
import fit.asta.health.common.ui.AppTheme
import kotlinx.coroutines.ExperimentalCoroutinesApi
@AndroidEntryPoint
class WalkingActivity : ComponentActivity() {
    companion object {

        fun launch(context: Context) {
            val intent = Intent(context, WalkingActivity::class.java)
            intent.apply {
                context.startActivity(this)
            }
        }
    }
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        val PHYSICAL_ACTIVITY = 123
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_DENIED) {
            //ask for permission
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACTIVITY_RECOGNITION), PHYSICAL_ACTIVITY)
        }

        super.onCreate(savedInstanceState)
        setContent {
            MyApp {
                StepsCounterNavigation(navController = rememberNavController(), hiltViewModel<WalkingViewModel>())
            }
        }
    }
}
@Composable
fun MyApp(context: @Composable () -> Unit) {
    AppTheme {
        context()
    }
}
@Preview
@Composable
fun WalkingToolHomeScreen() {

    Scaffold(topBar = {
        BottomNavigation(content = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_exercise_back),
                        contentDescription = null,
                        Modifier.size(24.dp)
                    )
                }
                Text(
                    text = "Step Counter",
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center
                )
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_physique),
                        contentDescription = null,
                        Modifier.size(24.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }, elevation = 10.dp, backgroundColor = MaterialTheme.colorScheme.onPrimary)
    }, content = {
        WalkingBottomSheet(paddingValues = it)
    })

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    androidx.compose.material.Surface(Modifier.fillMaxSize()) {
        WalkingToolHomeScreen()
    }

}