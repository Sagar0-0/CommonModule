package fit.asta.health.profilenew

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dagger.hilt.android.AndroidEntryPoint
import fit.asta.health.databinding.ActivityProfileNewBinding
import fit.asta.health.navigation.profile.SpiralDesignDetailsPhoto
import fit.asta.health.profilenew.viewmodel.ProfileViewModel
import java.time.format.TextStyle

@AndroidEntryPoint
class ProfileNewActivity : AppCompatActivity() {
    
    lateinit var binding: ActivityProfileNewBinding
    val viewModel: ProfileViewModel by viewModels()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityProfileNewBinding.inflate(layoutInflater)


        binding.profileComposeView.setContent {

            val profile = viewModel.profile.value


            if (profile!=null){
                Surface(color=Color.Black, modifier = Modifier.padding(20.dp)) {
                    Text(text = profile.physic.toString(), fontSize = 20.sp,color = Color.White)
                }
            }else{
                Text("No Data")
            }

        }

        setContentView(binding.root)
    }

}