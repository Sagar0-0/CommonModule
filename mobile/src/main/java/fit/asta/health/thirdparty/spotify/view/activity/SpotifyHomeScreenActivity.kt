package fit.asta.health.thirdparty.spotify.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import fit.asta.health.R
import fit.asta.health.databinding.ActivitySpotifyHomeScreenBinding

@AndroidEntryPoint
class SpotifyHomeScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySpotifyHomeScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySpotifyHomeScreenBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)
    }
}