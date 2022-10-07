package fit.asta.health.profilenew

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.Surface
import androidx.compose.ui.graphics.Color
import fit.asta.health.databinding.ActivityProfileNewBinding
import fit.asta.health.navigation.profile.SpiralDesignDetailsPhoto

class ProfileNewActivity : AppCompatActivity() {
    
    lateinit var binding: ActivityProfileNewBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityProfileNewBinding.inflate(layoutInflater)

        binding.profileComposeView.setContent {
            Surface(color = Color.White) {
                SpiralDesignDetailsPhoto()
            }
        }

        setContentView(binding.root)
    }

}