package fit.asta.health.profile

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import dagger.hilt.android.AndroidEntryPoint
import fit.asta.health.profile.model.domain.UserProfile
import fit.asta.health.profile.view.*
import fit.asta.health.profile.viewmodel.ProfileViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class UserProfileActivity : AppCompatActivity() {

    private val viewModel: ProfileViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val profileState = viewModel.state.collectAsState().value
            ProfileContent(profileState = profileState)
        }
    }

    @Composable
    fun ProfileScreenDetails(mainProfile: UserProfile?) {
        if (mainProfile != null) {
            Text(text = mainProfile.contact.toString())
        }
    }

    @Composable
    fun ProfileScreenPhysique() {
        Text(text = "physique")
    }

    @Composable
    fun ProfileScreenHealth() {
        Text("Health")
    }

    @Composable
    fun ProfileScreenLifeStyle() {
        Text("LifeStyle")
    }

    @Composable
    fun ProfileScreenDiet() {
        Text("Diet")
    }

}